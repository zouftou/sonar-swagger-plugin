package org.sonar.swagger.parser;

import org.junit.Test;

public class BOMTreeTest extends CommonSyntaxTokenTreeTest {

  public BOMTreeTest() {
    super(SwaggerLexicalGrammar.BOM);
  }

  @Test
  public void bom() {
    checkParsed("\ufeff");
  }

  @Test
  public void notBom() {
    checkNotParsed("\"\\ufeff\"");
  }

}
