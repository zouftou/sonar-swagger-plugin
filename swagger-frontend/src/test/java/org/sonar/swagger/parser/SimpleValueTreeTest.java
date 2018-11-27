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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SimpleValueTree;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;

import static org.fest.assertions.Assertions.assertThat;

public class SimpleValueTreeTest extends CommonSwaggerTreeTest {

  public SimpleValueTreeTest() {
    super(SwaggerLexicalGrammar.VALUE_SIMPLE);
  }

  @Test
  public void simpleValue() {
	SimpleValueTree tree;
	
	tree = checkParsed(" true");
	assertTrue(tree.value().is(Tree.Kind.TRUE));
	
	tree = checkParsed(" false");
	assertTrue(tree.value().is(Tree.Kind.FALSE));
	
	tree = checkParsed(" null");
	assertTrue(tree.value().is(Tree.Kind.NULL));
	
	tree = checkParsed(" abc");
	assertTrue(tree.value().is(Tree.Kind.STRING));
	
	tree = checkParsed(" 8");
	assertTrue(tree.value().is(Tree.Kind.NUMBER));
	
	tree = checkParsed(" 8.9");
	assertTrue(tree.value().is(Tree.Kind.NUMBER));
	
	tree = checkParsed(" -8.9");
	assertTrue(tree.value().is(Tree.Kind.NUMBER));
  }

  @Test
  public void notValue() {
    checkNotParsed("abc");
    checkNotParsed("true");
  }

  private SimpleValueTree checkParsed(String toParse) {
	SimpleValueTree tree = (SimpleValueTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

}
