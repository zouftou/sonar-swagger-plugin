package org.sonar.plugins.swagger;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.swagger.checks.CheckList;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SwaggerProfileTest {

  @Test
  public void should_create_sonarqube_way_profile() {
    ValidationMessages validation = ValidationMessages.create();
    SwaggerProfile definition = new SwaggerProfile(universalRuleFinder());
    RulesProfile profile = definition.createProfile(validation);

    assertThat(profile.getName()).isEqualTo(SwaggerProfile.SONARQUBE_WAY_PROFILE_NAME);
    assertThat(profile.getLanguage()).isEqualTo(SwaggerLanguage.KEY);
    //assertThat(profile.getActiveRulesByRepository(CheckList.REPOSITORY_KEY)).hasSize(5);
    assertThat(validation.hasErrors()).isFalse();
  }

  private RuleFinder universalRuleFinder() {
    RuleFinder ruleFinder = mock(RuleFinder.class);
    when(ruleFinder.findByKey(anyString(), anyString())).thenAnswer(new Answer<Rule>() {
      @Override
      public Rule answer(InvocationOnMock iom) throws Throwable {
        return Rule.create((String) iom.getArguments()[0], (String) iom.getArguments()[1], (String) iom.getArguments()[1]);
      }
    });

    return ruleFinder;
  }
}
