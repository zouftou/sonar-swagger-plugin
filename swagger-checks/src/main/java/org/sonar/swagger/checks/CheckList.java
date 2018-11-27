package org.sonar.swagger.checks;

import java.util.Collection;

import org.sonar.swagger.checks.generic.FileNameCheck;
import org.sonar.swagger.checks.generic.ParsingErrorCheck;
import org.sonar.swagger.checks.generic.VersionCheck;

import com.google.common.collect.ImmutableList;

public final class CheckList {

	public static final String REPOSITORY_KEY = "swagger";
	
	public static final String REPOSITORY_NAME = "SonarQube";

	private CheckList() {
	}

	@SuppressWarnings("rawtypes")
	public static Collection<Class> getChecks() {
		return ImmutableList.<Class>of(
				FileNameCheck.class,
				ParsingErrorCheck.class,
				VersionCheck.class
				);
	}
	
}
