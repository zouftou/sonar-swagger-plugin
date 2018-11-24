package org.sonar.swagger.visitors.metrics;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.typed.ActionParser;
import org.junit.Test;
import org.sonar.swagger.parser.SwaggerParserBuilder;
import org.sonar.plugins.swagger.api.tree.Tree;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class MetricsTest {

  private static final ActionParser<Tree> PARSER = SwaggerParserBuilder.createParser(Charsets.UTF_8);

  @Test
  public void metrics() {
    String path = "src/test/resources/metrics.json";
    Tree tree = PARSER.parse(new File(path));
    assertMetrics(tree);
  }

  @Test
  public void metrics_UTF8_file_with_BOM() {
    String path = "src/test/resources/metricsUtf8WithBom.json";
    Tree tree = PARSER.parse(new File(path));
    assertMetrics(tree);
  }

  private void assertMetrics(Tree tree) {
    assertThat(new LinesOfCodeVisitor(tree).getNumberOfLinesOfCode()).isEqualTo(6);
    assertThat(new StatementsVisitor(tree).getNumberOfStatements()).isEqualTo(7);
  }

}
