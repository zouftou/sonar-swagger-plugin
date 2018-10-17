package org.sonar.swagger.parser;

import org.junit.Test;

public class ColonTreeTest extends CommonSyntaxTokenTreeTest {

  public ColonTreeTest() {
    super(SwaggerLexicalGrammar.COLON, ":");
  }

  @Test
  public void comma() {
    checkParsed(":");
    checkParsed(" :");
    checkParsed("  :");
  }

  @Test
  public void notComma() {
    checkNotParsed(",");
    checkNotParsed(";");
  }

}
