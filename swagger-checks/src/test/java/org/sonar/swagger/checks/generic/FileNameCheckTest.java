package org.sonar.swagger.checks.generic;

import org.junit.Test;
import org.sonar.swagger.checks.CheckTestUtils;
import org.sonar.swagger.checks.util.SwaggerCheckVerifier;

import static org.fest.assertions.Assertions.assertThat;

public class FileNameCheckTest {

	@Test
	public void should_follow_a_custom_naming_convention_and_not_raise_an_issue() {
		FileNameCheck check = new FileNameCheck();
		check.setFormat("^[a-z][-a-z]+\\.yaml$");

		SwaggerCheckVerifier.verify(check, CheckTestUtils.getTestFile("myfile.yaml")).noMore();
	}

}
