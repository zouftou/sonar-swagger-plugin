package org.sonar.plugins.swagger;

import org.sonar.api.Plugin;

public class SwaggerPlugin implements Plugin {

	@Override
	public void define(Context context) {
	    context.addExtensions(
	    	      Swagger.class,
	    	      SwaggerSquidSensor.class,
	    	      SwaggerProfile.class,
	    	      SwaggerRulesDefinition.class);
	}
}
