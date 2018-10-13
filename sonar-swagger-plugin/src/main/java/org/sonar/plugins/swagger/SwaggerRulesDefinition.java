package org.sonar.plugins.swagger;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;
import org.sonar.swagger.checks.CheckList;

public class SwaggerRulesDefinition implements RulesDefinition {

	@Override
	public void define(Context context) {
	    NewRepository repository = context
	    	      .createRepository(CheckList.REPOSITORY_KEY, SwaggerLanguage.KEY)
	    	      .setName(CheckList.REPOSITORY_NAME);

	    new AnnotationBasedRulesDefinition(repository, SwaggerLanguage.KEY).addRuleClasses(false, CheckList.getChecks());
	    repository.done();
	}
}
