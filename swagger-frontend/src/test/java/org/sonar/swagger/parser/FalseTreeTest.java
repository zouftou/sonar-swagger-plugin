package org.sonar.swagger.parser;

import org.junit.Test;

public class FalseTreeTest extends CommonSyntaxTokenTreeTest {

  public FalseTreeTest() {
    super(SwaggerLexicalGrammar.FALSE, "false");
  }

  @Test
  public void isFalse() {
    checkParsed("false");
    checkParsed(" false");
    checkParsed("  false");
  }

  @Test
  public void notFalse() {
    checkNotParsed("FALSE");
  }

}
