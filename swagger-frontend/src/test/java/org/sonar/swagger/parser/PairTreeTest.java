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

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class PairTreeTest extends CommonSwaggerTreeTest {

  public PairTreeTest() {
    super(SwaggerLexicalGrammar.PAIR);
  }

  @Test
  public void pair() {
    PairTree tree;

    tree = checkParsed("key: \"value\"");
    assertThat(tree.key().actualText()).isEqualTo("key");
    assertTrue(tree.value().is(Tree.Kind.VALUE_SIMPLE));

    tree = checkParsed("key:\n  abc: 1\n  def: 2");
    ValueTree valueTree = (ValueTree)tree.value();
    assertThat(tree.key().actualText()).isEqualTo("key");
    assertTrue(valueTree.value().is(Tree.Kind.OBJECT));
    assertThat(((ObjectTree)valueTree.value()).entries().size()).isEqualTo(2);

    tree = checkParsed("key: null");
    assertTrue(tree.value().is(Tree.Kind.VALUE_SIMPLE));
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.NULL));

    tree = checkParsed("key: false");
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.FALSE));

    tree = checkParsed("key: true");
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.TRUE));

    tree = checkParsed("key: 1");
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.NUMBER));
  }

  @Test
  public void notPair() {
    checkNotParsed("123");
    checkNotParsed("\"abc\"");
    checkNotParsed("\"abc\":");
    checkNotParsed(":");
    checkNotParsed(": \"ab\"");
  }

  private PairTree checkParsed(String toParse) {
    PairTree tree = (PairTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.key()).isNotNull();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

}
