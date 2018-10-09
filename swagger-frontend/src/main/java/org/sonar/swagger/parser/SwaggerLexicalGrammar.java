package org.sonar.swagger.parser;

import com.sonar.sslr.api.GenericTokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

/*
 * See https://tools.ietf.org/html/rfc7159 and http://json.org/
 */
public enum SwaggerLexicalGrammar implements GrammarRuleKey {

  SWAGGER,
  OBJECT,
  PAIR,
  KEY,
  ARRAY,
  VALUE,

  TRUE,
  FALSE,
  NULL,

  STRING,
  NUMBER,

  COLON,
  HYPHEN,
  SPACE,

  BOM,
  EOF,

  SPACING;

  public static LexerlessGrammarBuilder createGrammar() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    syntax(b);
    b.setRootRule(SWAGGER);
    return b;
  }

  private static void syntax(LexerlessGrammarBuilder b) {
    b.rule(TRUE).is(SPACING, "true");
    b.rule(FALSE).is(SPACING, "false");
    b.rule(NULL).is(SPACING, "null");

    b.rule(NUMBER).is(SPACING, b.regexp("[-]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"));
    b.rule(STRING).is(SPACING, b.regexp("\"([^\"\\\\]*+(\\\\([\\\\\"/bfnrt]|u[0-9a-fA-F]{4}))?+)*+\""));

    b.rule(COLON).is(SPACING, ":");
    b.rule(HYPHEN).is(SPACING, "-");
    
    b.rule(BOM).is("\ufeff");
    b.rule(EOF).is(SPACING, b.token(GenericTokenType.EOF, b.endOfInput()));

    b.rule(SPACING).is(b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+")));
  }

}
