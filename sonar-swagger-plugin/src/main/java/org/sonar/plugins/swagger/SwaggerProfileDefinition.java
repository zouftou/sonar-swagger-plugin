package org.sonar.plugins.swagger;

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.squidbridge.annotations.AnnotationBasedProfileBuilder;
import org.sonar.swagger.checks.CheckList;

public class SwaggerProfileDefinition extends ProfileDefinition {

	private final RuleFinder ruleFinder;

	public static final String SONARQUBE_WAY_PROFILE_NAME = "Sonar Way";

	public SwaggerProfileDefinition(RuleFinder ruleFinder) {
		this.ruleFinder = ruleFinder;
	}

	@Override
	public RulesProfile createProfile(ValidationMessages messages) {
		AnnotationBasedProfileBuilder annotationBasedProfileBuilder = new AnnotationBasedProfileBuilder(ruleFinder);
		return annotationBasedProfileBuilder.build(
				CheckList.REPOSITORY_KEY, SONARQUBE_WAY_PROFILE_NAME,
				SwaggerLanguage.KEY,
				CheckList.getChecks(),
				messages);
	}
	
}
