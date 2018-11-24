package org.sonar.plugins.swagger.api;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import static org.fest.assertions.Assertions.assertThat;

public class CustomSwaggerRulesDefinitionTest {

  private static final String REPOSITORY_NAME = "Custom Rule Repository";
  private static final String REPOSITORY_KEY = "CustomRuleRepository";

  private static final String RULE_NAME = "This is my custom rule";
  private static final String RULE_KEY = "MyCustomRule";

  @Test
  public void test() {
	MyCustomSwaggerRulesDefinition rulesDefinition = new MyCustomSwaggerRulesDefinition();
    RulesDefinition.Context context = new RulesDefinition.Context();
    rulesDefinition.define(context);
    RulesDefinition.Repository repository = context.repository(REPOSITORY_KEY);

    assertThat(repository.name()).isEqualTo(REPOSITORY_NAME);
    assertThat(repository.language()).isEqualTo("swagger");
    assertThat(repository.rules()).hasSize(1);

    RulesDefinition.Rule customRule = repository.rule(RULE_KEY);
    assertThat(customRule).isNotNull();
    assertThat(customRule.key()).isEqualTo(RULE_KEY);
    assertThat(customRule.name()).isEqualTo(RULE_NAME);

    RulesDefinition.Param param = repository.rules().get(0).params().get(0);
    assertThat(param.key()).isEqualTo("customParam");
    assertThat(param.description()).isEqualTo("Custom parameter");
    assertThat(param.defaultValue()).isEqualTo("Default value");
  }

  @Rule(
    key = RULE_KEY,
    name = RULE_NAME,
    description = "desc",
    tags = {"bug"})
  public class MyCustomRule extends DoubleDispatchVisitor {
    @RuleProperty(
      key = "customParam",
      description = "Custom parameter",
      defaultValue = "Default value")
    public String customParam = "value";
  }

  public static class MyCustomSwaggerRulesDefinition extends CustomSwaggerRulesDefinition {

    @Override
    public String repositoryName() {
      return REPOSITORY_NAME;
    }

    @Override
    public String repositoryKey() {
      return REPOSITORY_KEY;
    }

    @Override
    public Class[] checkClasses() {
      return new Class[]{MyCustomRule.class};
    }
  }

}
