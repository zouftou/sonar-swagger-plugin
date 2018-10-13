package org.sonar.plugins.swagger;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Rule;
import org.sonar.swagger.checks.CheckList;
import org.sonar.swagger.checks.generic.FileNameCheck;

import static org.fest.assertions.Assertions.assertThat;

public class SwaggerRulesDefinitionTest {

  @Test
  public void test() {
    SwaggerRulesDefinition rulesDefinition = new SwaggerRulesDefinition();
    RulesDefinition.Context context = new RulesDefinition.Context();
    rulesDefinition.define(context);
    RulesDefinition.Repository repository = context.repository("swagger");

    assertThat(repository.name()).isEqualTo("SonarQube");
    assertThat(repository.language()).isEqualTo("swagger");
    assertThat(repository.rules()).hasSize(CheckList.getChecks().size());

    RulesDefinition.Rule fileNameRule = repository.rule(FileNameCheck.class.getAnnotation(Rule.class).key());
    assertThat(fileNameRule).isNotNull();
    assertThat(fileNameRule.name()).isEqualTo(FileNameCheck.class.getAnnotation(Rule.class).name());
  }

}
