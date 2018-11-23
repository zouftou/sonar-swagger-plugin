package org.sonar.swagger.parser;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class SwaggerTreeTest extends CommonSwaggerTreeTest {

	public SwaggerTreeTest() {
		super(SwaggerLexicalGrammar.SWAGGER);
	}

	@Test
	public void swagger() throws IOException {
		checkParsed("");
		checkParsed(" ");
		checkParsed("swagger:\r\n  info: hhh");
		checkParsed("swagger:\n- \"rrr\"\n- \"sss\"");
		checkParsed("swagger:\n- \"rrrr\"\n- \'ssss\'");
		checkParsed("swagger: \"2.0\"\ninfo:\n  description: \"Orange FQC Traceability API definition\"\n  title: \"test\"");
		checkParsed("swagger: '2.0'\ninfo:\n  description: Orange FQC Traceability API definition\n  title: \"test\"");
		checkParsed("swagger: \"2.0\"\ninfo:\n  description: \"Orange FQC Traceability API definition\"\n  title: \"test\"");

		checkParsed(new File("src/test/resources/entry.yaml"));
		checkParsed(new File("src/test/resources/array.yaml"));
		checkParsed(new File("src/test/resources/arrayOfObject.yaml"));
		checkParsed(new File("src/test/resources/arrayOfArray.yaml"));
		checkParsed(new File("src/test/resources/arrayOfArrayVoid.yaml"));
		checkParsed(new File("src/test/resources/ObjectWithArray.yaml"));
		checkParsed(new File("src/test/resources/petstore.yaml"));
		checkParsed(new File("src/test/resources/orange.yaml"));
	}

	@Test
	public void notSwagger() {
		checkNotParsed("khbhb");
		checkNotParsed("swagger:");
		checkNotParsed("#");
		checkNotParsed("blabla");
		checkNotParsed("\"abc\": 10");
		checkNotParsed("true, false");
	}

	private void checkParsed(String toParse) throws IOException {
		SwaggerTree tree = (SwaggerTree) parser().parse(toParse);
		assertThat(tree).isNotNull();
	}

	private void checkParsed(File file) throws IOException {
		checkParsed(Files.toString(file, Charsets.UTF_8));
	}
}
