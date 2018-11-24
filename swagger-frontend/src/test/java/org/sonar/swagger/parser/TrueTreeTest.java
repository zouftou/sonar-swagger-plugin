package org.sonar.swagger.parser;

import org.junit.Test;

public class TrueTreeTest extends CommonSyntaxTokenTreeTest {

  public TrueTreeTest() {
    super(SwaggerLexicalGrammar.TRUE, "true");
  }

  @Test
  public void isTrue() {
    checkParsed("true");
    checkParsed(" true", "true");
    checkParsed("  true", "true");
  }

  @Test
  public void notTrue() {
    checkNotParsed("TRUE");
  }

}
