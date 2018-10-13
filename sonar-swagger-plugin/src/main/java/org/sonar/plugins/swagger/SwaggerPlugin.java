package org.sonar.plugins.swagger;

import org.sonar.api.Plugin;

public class SwaggerPlugin implements Plugin {

	@Override
	public void define(Context context) {
	    context.addExtensions(
	    	      SwaggerLanguage.class,
	    	      SwaggerSquidSensor.class,
	    	      SwaggerProfile.class,
	    	      SwaggerRulesDefinition.class);
	}
}
