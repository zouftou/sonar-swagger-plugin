package org.sonar.swagger.parser;

import org.sonar.plugins.swagger.api.SwaggerKeyword;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import com.sonar.sslr.api.GenericTokenType;

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

  COMMA,
  COLON,
  MINUS,
  SINGLE_QUOTE,
  DOUBLE_QUOTE,

  LEFT_BRACKET,
  RIGHT_BRACKET,

  BOM,
  EOF,
  
  SPACING;

  public static LexerlessGrammarBuilder createGrammar() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    tokens(b);
    types(b);
    keywords(b);
    b.setRootRule(SWAGGER);
    return b;
  }
  
  private static void tokens(LexerlessGrammarBuilder b) {
	
    b.rule(COMMA).is(SPACING, ",");
    b.rule(COLON).is(":");
    b.rule(MINUS).is(SPACING, "-");
    b.rule(SINGLE_QUOTE).is(SPACING, "'");
    b.rule(DOUBLE_QUOTE).is(SPACING, "\"");
    
    b.rule(LEFT_BRACKET).is(SPACING, "[");
    b.rule(RIGHT_BRACKET).is(SPACING, "]");

    b.rule(BOM).is("\ufeff");
    b.rule(EOF).is(SPACING, b.token(GenericTokenType.EOF, b.endOfInput()));
  }

  private static void types(LexerlessGrammarBuilder b) {
    b.rule(TRUE).is(SPACING, "true");
    b.rule(FALSE).is(SPACING, "false");
    b.rule(NULL).is(SPACING, "null");

    b.rule(NUMBER).is(SPACING, b.regexp("[-]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"));
    b.rule(STRING).is(SPACING, b.regexp("([^\"\'\\\\]*+(\\\\([\\\\\"/bfnrt]|u[0-9a-fA-F]{4}))?+)*+"));
    //b.rule(SINGLE_QUOTED_STRING).is(SPACING, b.regexp("\'([^\'\\\\]*+(\\\\([\\\\\"/bfnrt]|u[0-9a-fA-F]{4}))?+)*+\'"));
    //b.rule(DOUBLE_QUOTED_STRING).is(SPACING, b.regexp("\"([^\"\\\\]*+(\\\\([\\\\\"/bfnrt]|u[0-9a-fA-F]{4}))?+)*+\""));

    b.rule(SPACING).is(b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+")));
  }
  
  private static void keywords(LexerlessGrammarBuilder b) {
    for (SwaggerKeyword tokenType : SwaggerKeyword.values()) {
        b.rule(tokenType).is(SPACING, tokenType.getValue());
    }
  }

}
