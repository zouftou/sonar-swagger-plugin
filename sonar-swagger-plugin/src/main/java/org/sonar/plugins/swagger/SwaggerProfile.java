package org.sonar.plugins.swagger;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.swagger.checks.CheckList;
import org.sonar.squidbridge.annotations.AnnotationBasedProfileBuilder;

public class SwaggerProfile extends ProfileDefinition {

	private final RuleFinder ruleFinder;

	public static final String SONARQUBE_WAY_PROFILE_NAME = "SonarQube Way";

	public SwaggerProfile(RuleFinder ruleFinder) {
		this.ruleFinder = ruleFinder;
	}

	@Override
	public RulesProfile createProfile(ValidationMessages messages) {
		AnnotationBasedProfileBuilder annotationBasedProfileBuilder = new AnnotationBasedProfileBuilder(ruleFinder);
		return annotationBasedProfileBuilder.build(
				CheckList.REPOSITORY_KEY,
				SONARQUBE_WAY_PROFILE_NAME,
				SwaggerLanguage.KEY,
				CheckList.getChecks(),
				messages);
	}
}
