package org.sonar.swagger.parser;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.KeyTree;

import static org.fest.assertions.Assertions.assertThat;

public class KeyTreeTest extends CommonSwaggerTreeTest {

  public KeyTreeTest() {
    super(SwaggerLexicalGrammar.KEY);
  }

  @Test
  public void key() {
    checkParsed("swagger");
    checkParsed("abc_def");
    checkParsed("write:pets");
  }

  @Test
  public void notKey() {
    checkNotParsed("");
  }

  private void checkParsed(String toParse) {
    KeyTree tree = (KeyTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.value()).isNotNull();
    assertThat(tree.actualText()).isNotNull();
  }

}
