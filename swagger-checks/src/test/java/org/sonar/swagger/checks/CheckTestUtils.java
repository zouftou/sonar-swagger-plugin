package org.sonar.swagger.checks;

import java.io.File;

public class CheckTestUtils {

	private CheckTestUtils() {
	}

	public static File getTestFile(String relativePath) {
		return new File("src/test/resources/checks/" + relativePath);
	}

}
