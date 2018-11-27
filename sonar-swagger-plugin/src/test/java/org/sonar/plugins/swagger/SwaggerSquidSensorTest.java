package org.sonar.plugins.swagger;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.Collection;

import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.FileMetadata;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.sensor.issue.Issue;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;
import org.sonar.swagger.checks.CheckList;
import org.sonar.swagger.checks.generic.ParsingErrorCheck;
import org.sonar.swagger.checks.generic.VersionCheck;

import com.google.common.base.Charsets;

public class SwaggerSquidSensorTest {

	private final File baseDir = new File("src/test/resources");
	private final SensorContextTester context = SensorContextTester.create(baseDir);
	private CheckFactory checkFactory = new CheckFactory(mock(ActiveRules.class));

	@Test
	public void should_create_a_valid_sensor_descriptor() {
		DefaultSensorDescriptor descriptor = new DefaultSensorDescriptor();
		createSwaggerSquidSensor().describe(descriptor);
		assertThat(descriptor.name()).isEqualTo("Swagger Squid Sensor");
		assertThat(descriptor.languages()).containsOnly("swagger");
		assertThat(descriptor.type()).isEqualTo(InputFile.Type.MAIN);
	}

	@Test
	public void should_execute_and_compute_valid_measures_on_UTF8_file_without_BOM() {
		String relativePath = "sampleUTF8WithBOM.yml";
		inputFile(relativePath);
		createSwaggerSquidSensor().execute(context);
		assertMeasures("moduleKey:" + relativePath);
	}

	@Test
	public void should_execute_and_compute_valid_measures_on_UTF8_file_with_BOM() {
		String relativePath = "sample.yml";
		inputFile(relativePath);
		createSwaggerSquidSensor().execute(context);
		assertMeasures("moduleKey:" + relativePath);
	}

	private void assertMeasures(String key) {
		assertThat(context.measure(key, CoreMetrics.NCLOC).value()).isEqualTo(5);
		assertThat(context.measure(key, CoreMetrics.STATEMENTS).value()).isEqualTo(5);
	}
	
	@Test
	public void should_execute_and_save_version_issue_on_UTF8_file_with_BOM() {
		should_execute_and_save_issues("sampleUTF8WithBOM.yml", VersionCheck.class);
	}

	@Test
	public void should_execute_and_save_version_issue_on_UTF8_file_without_BOM() {
		should_execute_and_save_issues("sample.yml", VersionCheck.class);
	}

	private void should_execute_and_save_issues(String fileName, Class<?> clazz) {
		inputFile(fileName);

		ActiveRules activeRules = (new ActiveRulesBuilder())
				.create(RuleKey.of(CheckList.REPOSITORY_KEY, clazz.getAnnotation(Rule.class).key()))
				.activate().build();
		checkFactory = new CheckFactory(activeRules);

		createSwaggerSquidSensor().execute(context);

		assertThat(context.allIssues()).hasSize(1);
	}

	@Test
	public void should_raise_an_issue_because_the_parsing_error_rule_is_activated() {
		inputFile("parsingError.yml");

		ActiveRules activeRules = (new ActiveRulesBuilder()).create(
				RuleKey.of(CheckList.REPOSITORY_KEY, ParsingErrorCheck.class.getAnnotation(Rule.class).key())).activate().build();

		checkFactory = new CheckFactory(activeRules);

		context.setActiveRules(activeRules);
		createSwaggerSquidSensor().execute(context);
		Collection<Issue> issues = context.allIssues();
		assertThat(issues).hasSize(1);
		Issue issue = issues.iterator().next();
		assertThat(issue.primaryLocation().textRange().start().line()).isEqualTo(1);
	}

	@Test
	public void should_not_raise_any_issue_because_the_parsing_error_rule_is_not_activated() {
		inputFile("parsingError.yml");

		ActiveRules activeRules = new ActiveRulesBuilder().build();
		checkFactory = new CheckFactory(activeRules);

		context.setActiveRules(activeRules);
		createSwaggerSquidSensor().execute(context);
		Collection<Issue> issues = context.allIssues();
		assertThat(issues).hasSize(0);
	}

	private SwaggerSquidSensor createSwaggerSquidSensor() {
		SwaggerSquidSensor swaggerSquidSensor = new SwaggerSquidSensor(context.fileSystem(), checkFactory, null);
		return swaggerSquidSensor;
	}

	private void inputFile(String relativePath) {
		DefaultInputFile inputFile = new DefaultInputFile("moduleKey", relativePath).setModuleBaseDir(baseDir.toPath())
				.setType(InputFile.Type.MAIN).setLanguage(SwaggerLanguage.KEY);

		context.fileSystem().setEncoding(Charsets.UTF_8);
		context.fileSystem().add(inputFile);

		inputFile.initMetadata(new FileMetadata().readMetadata(inputFile.file(), Charsets.UTF_8));
	}

}
