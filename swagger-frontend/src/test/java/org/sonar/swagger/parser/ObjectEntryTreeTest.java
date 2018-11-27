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
import org.sonar.plugins.swagger.api.tree.ObjectEntryTree;
import org.sonar.plugins.swagger.api.tree.Tree;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ObjectEntryTreeTest extends CommonSwaggerTreeTest {

  public ObjectEntryTreeTest() {
    super(SwaggerLexicalGrammar.OBJECT_ENTRY);
  }

  @Test
  public void object() throws IOException {
    ObjectEntryTree tree;

    tree = checkParsed("  title: \"abc\"");
    assertTrue(tree.pair().value().is(Tree.Kind.VALUE_SIMPLE));
    
    tree = checkParsed("  title: 'abc'");
    assertTrue(tree.pair().value().is(Tree.Kind.VALUE_SIMPLE));
    
    tree = checkParsed("    title: abc");
    assertTrue(tree.pair().value().is(Tree.Kind.VALUE_SIMPLE));
  }

  @Test
  public void notObject() {
    checkNotParsed("title: \"abc\"");
    checkNotParsed(" title: \'abc\'");
    checkNotParsed("\"abc\"");
  }

  private ObjectEntryTree checkParsed(String toParse) throws IOException {
    ObjectEntryTree tree = (ObjectEntryTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

  private ObjectEntryTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
