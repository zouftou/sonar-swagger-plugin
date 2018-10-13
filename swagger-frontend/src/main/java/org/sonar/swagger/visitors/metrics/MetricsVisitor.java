package org.sonar.swagger.visitors.metrics;

import com.google.common.collect.ImmutableList;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.SubscriptionVisitor;

import java.io.Serializable;
import java.util.List;

public class MetricsVisitor extends SubscriptionVisitor {

	private final SensorContext sensorContext;
	private final FileSystem fileSystem;
	private InputFile inputFile;

	public MetricsVisitor(SensorContext sensorContext) {
		this.sensorContext = sensorContext;
		this.fileSystem = sensorContext.fileSystem();
	}

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.of(Tree.Kind.SWAGGER);
	}

	@Override
	public void visitFile(Tree tree) {
		this.inputFile = fileSystem.inputFile(fileSystem.predicates().is(getContext().getFile()));
	}

	@Override
	public void leaveFile(Tree tree) {
		LinesOfCodeVisitor linesOfCodeVisitor = new LinesOfCodeVisitor(tree);
		saveMetricOnFile(CoreMetrics.NCLOC, linesOfCodeVisitor.getNumberOfLinesOfCode());

		StatementsVisitor statementsVisitor = new StatementsVisitor(tree);
		saveMetricOnFile(CoreMetrics.STATEMENTS, statementsVisitor.getNumberOfStatements());
	}

	private <T extends Serializable> void saveMetricOnFile(Metric metric, T value) {
		sensorContext.<T>newMeasure().withValue(value).forMetric(metric).on(inputFile).save();
	}

}
