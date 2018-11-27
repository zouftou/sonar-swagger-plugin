/*
 * SonarQube Swagger Analyzer
 * Copyright (C) 2015-2017 Zouhir OUFTOU
 * zouhir.ouftou@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.swagger;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.typed.ActionParser;

import java.io.File;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.swagger.checks.CheckList;
import org.sonar.swagger.checks.generic.ParsingErrorCheck;
import org.sonar.swagger.parser.SwaggerParserBuilder;
import org.sonar.swagger.visitors.CharsetAwareVisitor;
import org.sonar.swagger.visitors.SwaggerVisitorContext;
import org.sonar.swagger.visitors.SyntaxHighlighterVisitor;
import org.sonar.swagger.visitors.metrics.MetricsVisitor;
import org.sonar.plugins.swagger.api.CustomSwaggerRulesDefinition;
import org.sonar.plugins.swagger.api.SwaggerCheck;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.TreeVisitor;
import org.sonar.plugins.swagger.api.visitors.issue.Issue;
import org.sonar.squidbridge.ProgressReport;
import org.sonar.squidbridge.api.AnalysisException;

public class SwaggerSquidSensor implements Sensor {

	private static final Logger LOG = Loggers.get(SwaggerSquidSensor.class);

	private final FileSystem fileSystem;
	private final SwaggerChecks checks;
	private final ActionParser<Tree> parser;
	private final FilePredicate mainFilePredicate;
	private IssueSaver issueSaver;
	private RuleKey parsingErrorRuleKey = null;

	public SwaggerSquidSensor(FileSystem fileSystem, CheckFactory checkFactory) {
		this(fileSystem, checkFactory, null);
	}

	public SwaggerSquidSensor(FileSystem fileSystem, CheckFactory checkFactory,
			@Nullable CustomSwaggerRulesDefinition[] customRulesDefinition) {
		this.fileSystem = fileSystem;

		this.mainFilePredicate = fileSystem.predicates().and(fileSystem.predicates().hasType(InputFile.Type.MAIN),
				fileSystem.predicates().hasLanguage(SwaggerLanguage.KEY));

		this.parser = SwaggerParserBuilder.createParser(fileSystem.encoding());

		this.checks = SwaggerChecks.createSwaggerCheck(checkFactory)
				.addChecks(CheckList.REPOSITORY_KEY, CheckList.getChecks()).addCustomChecks(customRulesDefinition);
	}

	@Override
	public void describe(SensorDescriptor descriptor) {
		descriptor.onlyOnLanguage(SwaggerLanguage.KEY).name("Swagger Squid Sensor").onlyOnFileType(Type.MAIN);
	}

	@Override
	public void execute(SensorContext sensorContext) {
		List<TreeVisitor> treeVisitors = Lists.newArrayList();
		treeVisitors.addAll(checks.visitorChecks());
		treeVisitors.add(new SyntaxHighlighterVisitor(sensorContext));
		treeVisitors.add(new MetricsVisitor(sensorContext));

		setParsingErrorCheckIfActivated(treeVisitors);

		ProgressReport progressReport = new ProgressReport("Report about progress of Swagger analyzer",
				TimeUnit.SECONDS.toMillis(10));
		progressReport.start(Lists.newArrayList(fileSystem.files(mainFilePredicate)));

		issueSaver = new IssueSaver(sensorContext, checks);
		List<Issue> issues = new ArrayList<>();

		boolean success = false;
		try {
			for (InputFile inputFile : fileSystem.inputFiles(mainFilePredicate)) {
				LOG.debug("Analyzing " + inputFile.absolutePath() + "...");
				issues.addAll(analyzeFile(sensorContext, inputFile, treeVisitors));
				progressReport.nextFile();
			}
			saveSingleFileIssues(issues);
			success = true;
		} finally {
			stopProgressReport(progressReport, success);
		}
	}

	private List<Issue> analyzeFile(SensorContext sensorContext, InputFile inputFile, List<TreeVisitor> visitors) {
		try {
			SwaggerTree swaggerTree = (SwaggerTree) parser.parse(new File(inputFile.absolutePath()));
			return scanFile(inputFile, swaggerTree, visitors);

		} catch (RecognitionException e) {
			checkInterrupted(e);
			LOG.error("Unable to parse file: " + inputFile.absolutePath());
			LOG.error(e.getMessage());
			processRecognitionException(e, sensorContext, inputFile);

		} catch (Exception e) {
			checkInterrupted(e);
			throw new AnalysisException("Unable to analyse file: " + inputFile.absolutePath(), e);
		}
		return new ArrayList<>();
	}

	private List<Issue> scanFile(InputFile inputFile, SwaggerTree swagger, List<TreeVisitor> visitors) {
		SwaggerVisitorContext context = new SwaggerVisitorContext(swagger, inputFile.file());
		List<Issue> issues = new ArrayList<>();
		for (TreeVisitor visitor : visitors) {
			if (visitor instanceof CharsetAwareVisitor) {
				((CharsetAwareVisitor) visitor).setCharset(fileSystem.encoding());
			}
			if (visitor instanceof SwaggerCheck) {
				issues.addAll(((SwaggerCheck) visitor).scanFile(context));
			} else {
				visitor.scanTree(context);
			}
		}
		return issues;
	}

	private void saveSingleFileIssues(List<Issue> issues) {
		for (Issue issue : issues) {
			issueSaver.saveIssue(issue);
		}
	}

	private void processRecognitionException(RecognitionException e, SensorContext sensorContext, InputFile inputFile) {
		if (parsingErrorRuleKey != null) {
			NewIssue newIssue = sensorContext.newIssue();

			NewIssueLocation primaryLocation = newIssue.newLocation().message(e.getMessage()).on(inputFile)
					.at(inputFile.selectLine(e.getLine()));

			newIssue.forRule(parsingErrorRuleKey).at(primaryLocation).save();
		}
	}

	private void setParsingErrorCheckIfActivated(List<TreeVisitor> treeVisitors) {
		for (TreeVisitor check : treeVisitors) {
			if (check instanceof ParsingErrorCheck) {
				parsingErrorRuleKey = checks.ruleKeyFor((SwaggerCheck) check);
				break;
			}
		}
	}

	private static void stopProgressReport(ProgressReport progressReport, boolean success) {
		if (success) {
			progressReport.stop();
		} else {
			progressReport.cancel();
		}
	}

	private static void checkInterrupted(Exception e) {
		Throwable cause = Throwables.getRootCause(e);
		if (cause instanceof InterruptedException || cause instanceof InterruptedIOException) {
			throw new AnalysisException("Analysis cancelled", e);
		}
	}
	
}