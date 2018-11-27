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
