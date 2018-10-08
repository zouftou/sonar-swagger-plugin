package org.sonar.plugins.swagger;

import org.sonar.api.Plugin;
import com.google.common.collect.ImmutableList;

public class SwaggerPlugin implements Plugin {

	@Override
	public void define(Context context) {
		ImmutableList.Builder<Object> builder = ImmutableList.builder();
		builder.add(
				SwaggerRulesDefinition.class,
				SwaggerSquidSensor.class);
		context.addExtensions(builder.build());
	}
}
