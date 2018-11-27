/*
 * SonarQube Swagger Analyzer
 * Copyright (C) 2015-2017 Zouhir OUFTOU
 * zouhir.ouftou@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
		//checkParsed(new File("src/test/resources/petstore.yaml"));
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
