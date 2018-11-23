package org.sonar.plugins.swagger;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SwaggerLanguageTest {

	@Test
	public void language_key_and_name() {
		SwaggerLanguage swagger = new SwaggerLanguage();
		assertThat(swagger.getKey()).isEqualTo("swagger");
		assertThat(swagger.getName()).isEqualTo("Swagger");
	}

	@Test
	public void default_file_suffix() {
		SwaggerLanguage swagger = new SwaggerLanguage();
		assertThat(swagger.getFileSuffixes()).containsOnly(".yaml", ".yml");
	}
	
}
