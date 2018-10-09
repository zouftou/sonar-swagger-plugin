package org.sonar.plugins.swagger;

import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;
import org.sonar.swagger.checks.CheckList;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public class SwaggerRulesDefinition implements RulesDefinition {

	@Override
	public void define(Context context) {
	    NewRepository repository = context
	    	      .createRepository(CheckList.REPOSITORY_KEY, Swagger.KEY)
	    	      .setName(CheckList.REPOSITORY_NAME);

	    List<Class> checks = getChecks();
	    new RulesDefinitionAnnotationLoader().load(repository, Iterables.toArray(checks, Class.class));
	    repository.done();
	}
	
  private List<Class> getChecks() {
    ImmutableList.Builder<Class> checksBuilder = ImmutableList.<Class>builder().addAll(CheckList.getChecks());
    return checksBuilder.build();
  }

}
