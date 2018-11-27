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
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.InternalArrayTree;
import org.sonar.plugins.swagger.api.tree.Tree;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ArrayInternalTreeTest extends CommonSwaggerTreeTest {

  public ArrayInternalTreeTest() {
    super(SwaggerLexicalGrammar.ARRAY_INTERNAL);
  }

  @Test
  public void internalArray() throws IOException {
    InternalArrayTree tree;

    tree = checkParsed(" tab:\n- ftp\n- \"http\"");
    assertTrue(tree.arrayTree().is(Tree.Kind.ARRAY));
    assertThat(tree.arrayTree().entries().size()).isEqualTo(2);

  }

  @Test
  public void notArray() {
    checkNotParsed("tab:\n- ftp\n- \"http\"");
    checkNotParsed("- ftp\n- \"http\"");
  }

  private InternalArrayTree checkParsed(String toParse) throws IOException {
	  InternalArrayTree tree = (InternalArrayTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.arrayTree()).isNotNull();
    return tree;
  }

  private InternalArrayTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
