package org.sonar.swagger.visitors.metrics;

import com.google.common.base.Charsets;
import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.swagger.parser.SwaggerParserBuilder;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.visitors.TreeVisitorContext;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetricsVisitorTest {

  @Test
  public void test() {
    File moduleBaseDir = new File("src/test/resources/");
    SensorContextTester context = SensorContextTester.create(moduleBaseDir);

    DefaultInputFile inputFile = new DefaultInputFile("moduleKey", "metrics.json")
      .setModuleBaseDir(moduleBaseDir.toPath())
      .setLanguage("json")
      .setType(InputFile.Type.MAIN);

    context.fileSystem().add(inputFile);

    MetricsVisitor metricsVisitor = new MetricsVisitor(context);

    TreeVisitorContext treeVisitorContext = mock(TreeVisitorContext.class);
    when(treeVisitorContext.getFile()).thenReturn(inputFile.file());
    when(treeVisitorContext.getTopTree()).thenReturn((SwaggerTree) SwaggerParserBuilder.createParser(Charsets.UTF_8).parse(inputFile.file()));

    metricsVisitor.scanTree(treeVisitorContext);

    String componentKey = "moduleKey:metrics.json";
    assertThat(context.measure(componentKey, CoreMetrics.NCLOC).value()).isEqualTo(6);
    assertThat(context.measure(componentKey, CoreMetrics.STATEMENTS).value()).isEqualTo(7);
  }

}
