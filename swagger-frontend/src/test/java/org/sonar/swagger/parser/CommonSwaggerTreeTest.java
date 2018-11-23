package org.sonar.swagger.parser;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.sonar.sslr.api.typed.ActionParser;
import org.sonar.plugins.swagger.api.tree.Tree;

import java.io.File;

import static org.junit.Assert.fail;

public abstract class CommonSwaggerTreeTest {

	private final ActionParser<Tree> parser;

	public CommonSwaggerTreeTest(SwaggerLexicalGrammar ruleKey) {
		parser = SwaggerParserBuilder.createTestParser(Charsets.UTF_8, ruleKey);
	}

	public ActionParser<Tree> parser() {
		return parser;
	}

	public void checkNotParsed(String toParse) {
		try {
			parser.parse(toParse);
		} catch (Exception e) {
			return;
		}
		fail("Did not throw a RecognitionException as expected.");
	}

	public void checkNotParsed(File file) {
		try {
			parser.parse(Files.toString(file, Charsets.UTF_8));
		} catch (Exception e) {
			return;
		}
		fail("Did not throw a RecognitionException as expected.");
	}

}
