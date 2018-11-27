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
