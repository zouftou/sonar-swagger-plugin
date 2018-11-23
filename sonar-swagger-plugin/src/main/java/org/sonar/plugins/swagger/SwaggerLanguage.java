package org.sonar.plugins.swagger;

import org.sonar.api.resources.AbstractLanguage;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class SwaggerLanguage extends AbstractLanguage {

	public static final String KEY = "swagger";

	public static final String NAME = "Swagger";

	public static final String DEFAULT_FILE_SUFFIXES = ".yaml,.yml";

	public SwaggerLanguage() {
		super(KEY, NAME);
	}

	@Override
	public String[] getFileSuffixes() {
		String[] suffixes = Iterables.toArray(Splitter.on(',').split(DEFAULT_FILE_SUFFIXES), String.class);
		return suffixes;
	}
	
}
