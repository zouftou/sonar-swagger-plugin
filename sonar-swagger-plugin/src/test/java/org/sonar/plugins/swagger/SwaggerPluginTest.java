package org.sonar.plugins.swagger;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.sonar.api.Plugin;
import org.sonar.api.utils.Version;

public class SwaggerPluginTest {

	@Test
	public void should_get_the_right_version() {
		Plugin.Context context = new Plugin.Context(Version.create(5, 6));
		new SwaggerPlugin().define(context);
		assertThat(context.getSonarQubeVersion().major()).isEqualTo(5);
		assertThat(context.getSonarQubeVersion().minor()).isEqualTo(6);
	}

	@Test
	public void should_get_the_right_number_of_extensions() {
		Plugin.Context context = new Plugin.Context(Version.create(5, 6));
		new SwaggerPlugin().define(context);
		assertThat(context.getExtensions()).hasSize(4);
	}
	
}
