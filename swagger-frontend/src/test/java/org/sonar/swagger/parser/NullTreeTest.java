package org.sonar.swagger.parser;

import org.junit.Test;

public class NullTreeTest extends CommonSyntaxTokenTreeTest {

  public NullTreeTest() {
    super(SwaggerLexicalGrammar.NULL, "null");
  }

  @Test
  public void isNull() {
    checkParsed("null");
    checkParsed(" null", "null");
    checkParsed("  null", "null");
  }

  @Test
  public void notNull() {
    checkNotParsed("NULL");
    checkNotParsed("\"NULL\"");
  }

}
