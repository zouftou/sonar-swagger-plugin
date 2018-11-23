package org.sonar.swagger.checks.generic;

import org.junit.Test;
import org.sonar.swagger.checks.CheckTestUtils;
import org.sonar.swagger.checks.generic.VersionCheck;
import org.sonar.swagger.checks.util.SwaggerCheckVerifier;

public class TabCharacterCheckTest {

	@Test
	public void should_follow_the_default_naming_convention_and_not_raise_an_issue() {
		SwaggerCheckVerifier.verify(new VersionCheck(), CheckTestUtils.getTestFile("myfile.yaml")).noMore();
	}
}
