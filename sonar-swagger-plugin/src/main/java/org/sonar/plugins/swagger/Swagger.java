package org.sonar.plugins.swagger;

import java.util.Arrays;

import org.sonar.api.config.Configuration;
import org.sonar.api.resources.AbstractLanguage;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class Swagger extends AbstractLanguage {

	public static final String KEY = "swagger";
	
	public static final String NAME = "Swagger";

	public static final String FILE_SUFFIXES_KEY = "sonar.swagger.file.suffixes";

	public static final String DEFAULT_FILE_SUFFIXES = ".yaml,.yml";

	private final Configuration settings;

	public Swagger(Configuration settings) {
		super(KEY, NAME);
		this.settings = settings;
	}

	@Override
	public String[] getFileSuffixes() {
		String[] suffixes = Arrays.stream(settings.getStringArray(Swagger.FILE_SUFFIXES_KEY))
				.filter(s -> s != null && !s.trim()
				.isEmpty())
				.toArray(String[]::new);
		if (suffixes.length == 0) {
			suffixes = Iterables.toArray(Splitter.on(',').split(DEFAULT_FILE_SUFFIXES), String.class);
		}
		return suffixes;
	}
}
