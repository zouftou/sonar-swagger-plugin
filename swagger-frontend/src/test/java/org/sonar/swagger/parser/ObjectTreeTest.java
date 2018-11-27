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

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.ObjectTree;
import org.sonar.plugins.swagger.api.tree.SimpleValueTree;
import org.sonar.plugins.swagger.api.tree.Tree;

import java.io.File;
import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class ObjectTreeTest extends CommonSwaggerTreeTest {

  public ObjectTreeTest() {
    super(SwaggerLexicalGrammar.OBJECT);
  }

  @Test
  public void object() throws IOException {
    ObjectTree tree;

    tree = checkParsed("  description: \"This is a\"");
    assertThat(tree.entries()).hasSize(1);
    assertTrue(tree.entries().get(0).pair().value().is(Tree.Kind.VALUE_SIMPLE));
    
    tree = checkParsed("  description: null");
    assertThat(tree.entries()).hasSize(1);
    assertTrue(((SimpleValueTree)tree.entries().get(0).pair().value()).value().is(Tree.Kind.NULL));
  }

  @Test
  public void notObject() {
    checkNotParsed("info:");
    checkNotParsed("info:\\n");
    checkNotParsed("info:\n  description:");
  }

  private ObjectTree checkParsed(String toParse) throws IOException {
    ObjectTree tree = (ObjectTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

  private ObjectTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
