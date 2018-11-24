package org.sonar.swagger.parser;

import com.google.common.base.Charsets;
import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class NumberTreeTest extends CommonSyntaxTokenTreeTest {

  public NumberTreeTest() {
    super(SwaggerLexicalGrammar.NUMBER);
  }

  @Test
  public void number() {
    checkParsed("0");
    checkParsed(" 0", "0");
    checkParsed("  0", "0");
    checkParsed("1");
    checkParsed("123");
    checkParsed("0.5");
    checkParsed("1.4");
    checkParsed("10.99");
    checkParsed("-1");
    checkParsed("-123");
    checkParsed("-0.5");
    checkParsed("-1.4");
    checkParsed("-10.99");
    checkParsed("1e10");
    checkParsed("1E10");
    checkParsed("1e-10");
    checkParsed("1E-10");
    checkParsed("1e+10");
    checkParsed("1E+10");
    checkParsed("-1e10");
    checkParsed("-1E10");
    checkParsed("-1e-10");
    checkParsed("-1E-10");
    checkParsed("-1e+10");
    checkParsed("-1E+10");
    checkParsed("10.5e10");
    checkParsed("10.5E10");
    checkParsed("10.5e-10");
    checkParsed("10.5E-10");
    checkParsed("10.5e+10");
    checkParsed("10.5E+10");
    checkParsed("-10.5e10");
    checkParsed("-10.5E10");
    checkParsed("-10.5e-10");
    checkParsed("-10.5E-10");
    checkParsed("-10.5e+10");
    checkParsed("-10.5E+10");
  }

  @Test
  public void notNumber() {
    checkNotParsed("abc");
    checkNotParsed("+1");
    checkNotParsed("+1.5.");
    checkNotParsed("0.");
    checkNotParsed("1.");
  }

  @Override
  public void checkNotParsed(String toParse) {
    try {
      SyntaxToken tree = (SyntaxToken) SwaggerParserBuilder.createTestParser(Charsets.UTF_8, SwaggerLexicalGrammar.NUMBER);
      if (!tree.text().equals(toParse)) {
        assertTrue(true);
        return;
      }
    } catch (Exception e) {
      return;
    }
    fail("Did not throw a RecognitionException as expected.");
  }

}
