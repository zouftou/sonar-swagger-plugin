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
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.UnnamedObjectTree;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ObjectUnnamedTreeTest extends CommonSwaggerTreeTest {

  public ObjectUnnamedTreeTest() {
    super(SwaggerLexicalGrammar.OBJECT_UNNAMED);
  }

  @Test
  public void unnamedObject() throws IOException {
	UnnamedObjectTree tree;

    tree = checkParsed("name: \"pet\"\n" + 
    		"  description: \"Everything about your Pets\"\n" + 
    		"  externalDocs:\n" + 
    		"    description: \"Find out more\"");
    assertThat(tree.key().actualText()).isEqualTo("name");
    assertThat(tree.objectTree().entries()).hasSize(2);
  }

  @Test
  public void notUnnamedObject() {
    checkNotParsed("name: ");
    checkNotParsed("name:\n");
    checkNotParsed("name: \"pet\"\n  description:");
  }

  private UnnamedObjectTree checkParsed(String toParse) throws IOException {
	UnnamedObjectTree tree = (UnnamedObjectTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

  private UnnamedObjectTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
