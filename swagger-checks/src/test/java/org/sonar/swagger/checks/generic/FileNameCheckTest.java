package org.sonar.swagger.checks.generic;

import org.junit.Test;
import org.sonar.swagger.checks.CheckTestUtils;
import org.sonar.swagger.checks.util.SwaggerCheckVerifier;

import static org.fest.assertions.Assertions.assertThat;

public class FileNameCheckTest {

	  @Test
	  public void should_follow_the_default_naming_convention_and_not_raise_an_issue() {
	    SwaggerCheckVerifier.verify(new FileNameCheck(), CheckTestUtils.getTestFile("structure.yaml"))
	      .noMore();
	  }

	  @Test
	  public void should_not_follow_the_default_naming_convention_and_raise_an_issue() {
		  SwaggerCheckVerifier.verify(new FileNameCheck(), CheckTestUtils.getTestFile("file-name.ko.yaml"))
	      .next().withMessage("Rename this file to match this regular expression: ^[A-Za-z][-_A-Za-z0-9]*\\.yaml$")
	      .noMore();
	  }

	  @Test
	  public void should_follow_a_custom_naming_convention_and_not_raise_an_issue() {
	    FileNameCheck check = new FileNameCheck();
	    check.setFormat("^[a-z][-a-z]+\\.yaml$");

	    SwaggerCheckVerifier.verify(check, CheckTestUtils.getTestFile("sample.yaml"))
	      .noMore();
	  }

	  @Test
	  public void should_not_follow_a_custom_naming_convention_and_raise_an_issue() {
	    FileNameCheck check = new FileNameCheck();
	    check.setFormat("^[a-z]+\\.yaml$");

	    SwaggerCheckVerifier.verify(check, CheckTestUtils.getTestFile("file-name.ko.yaml"))
	      .next().withMessage("Rename this file to match this regular expression: ^[a-z]+\\.yaml$")
	      .noMore();
	  }

	  @Test
	  public void should_throw_an_illegal_state_exception_as_the_format_parameter_regular_expression_is_not_valid() {
	    try {
	      FileNameCheck check = new FileNameCheck();
	      check.setFormat("(");
	      SwaggerCheckVerifier.verify(check, CheckTestUtils.getTestFile("sample.yaml")).noMore();
	    } catch (IllegalStateException e) {
	      assertThat(e.getMessage()).isEqualTo("Check swagger:file-name (File names should comply with a naming convention): " +
	        "format parameter \"(\" is not a valid regular expression.");
	    }
	  }
}
