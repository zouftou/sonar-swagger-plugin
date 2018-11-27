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

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;

public class ValueTreeTest extends CommonSwaggerTreeTest {

  public ValueTreeTest() {
    super(SwaggerLexicalGrammar.VALUE);
  }

  @Test
  public void value() {
	ValueTree tree;
	
    tree = checkParsed("\n- ftp\n- \"https\"\n- 'http'");
    assertTrue(tree.value().is(Tree.Kind.ARRAY));
    
    tree = checkParsed("\n  description: \"This is a\"");
    assertTrue(tree.value().is(Tree.Kind.OBJECT));
	  
  }

  @Test
  public void notValue() {
    checkNotParsed("\n");
    checkNotParsed("- ftp\n- \"https\"\n- 'http'");
    checkNotParsed("  description: \"This is a\"");
  }

  private ValueTree checkParsed(String toParse) {
    ValueTree tree = (ValueTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

}
