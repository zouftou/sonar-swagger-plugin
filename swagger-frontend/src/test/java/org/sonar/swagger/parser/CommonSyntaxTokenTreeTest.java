package org.sonar.swagger.parser;

import org.sonar.plugins.swagger.api.tree.SyntaxToken;

import static org.fest.assertions.Assertions.assertThat;

public abstract class CommonSyntaxTokenTreeTest extends CommonSwaggerTreeTest {

	private String expectedText;

	public CommonSyntaxTokenTreeTest(SwaggerLexicalGrammar ruleKey) {
		super(ruleKey);
		this.expectedText = null;
	}

	public CommonSyntaxTokenTreeTest(SwaggerLexicalGrammar ruleKey, String expectedText) {
		super(ruleKey);
		this.expectedText = expectedText;
	}

	public void checkParsed(String toParse, String expected) {
		SyntaxToken token = (SyntaxToken) parser().parse(toParse);
		assertThat(token.text()).isEqualTo(expected);
	}

	public void checkParsed(String toParse) {
		checkParsed(toParse, expectedText != null ? expectedText : toParse);
	}

}
