package org.sonar.swagger.parser;

import com.sonar.sslr.api.GenericTokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

/*
 * See https://tools.ietf.org/html/rfc7159 and http://json.org/
 */
public enum SwaggerLexicalGrammar implements GrammarRuleKey {

  // High level nodes
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
  
  //Tokens
  COLON,
  MINUS,
  SPACE,

  BOM,
  EOF,
  
  //Literals
  SINGLE_QUOTED_STRING_LITERAL,
  DOUBLE_QUOTED_STRING_LITERAL,

  NAME_LITERAL,
  VARIABLE_LITERAL,

  REF,
  
  //Spacing
  SPACING;

  public static LexerlessGrammarBuilder createGrammar() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    spacing(b);
    tokens(b);
    type(b);
    b.setRootRule(SWAGGER);
    return b;
  }

  private static void tokens(LexerlessGrammarBuilder b) {
    b.rule(BOM).is("\ufeff");
    b.rule(EOF).is(SPACING, b.token(GenericTokenType.EOF, b.endOfInput()));
    
    b.rule(COLON).is(SPACING, ":");
    b.rule(MINUS).is(SPACING, "-");
    b.rule(REF).is(SPACING, "$ref");
  }
  
  private static void spacing(LexerlessGrammarBuilder b) {

	String hashLineComment = "#[^\\n\\r]*+";
	String slashLineComment = "//[^\\n\\r]*+";
	String multiLineComment = "/\\*[\\s\\S]*?\\*/";
	String comment = "(?:" + hashLineComment + "|" + slashLineComment + "|" + multiLineComment + ")";

	b.rule(SPACING).is(b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+")),
			b.zeroOrMore(b.commentTrivia(b.regexp(comment)), b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+"))));
  }
  
  private static void type(LexerlessGrammarBuilder b) {
    b.rule(TRUE).is(SPACING, "true");
    b.rule(FALSE).is(SPACING, "false");
    b.rule(NULL).is(SPACING, "null");
    
    b.rule(DOUBLE_QUOTED_STRING_LITERAL).is(SPACING, b.regexp("\"([^\"\\\\]*+(\\\\[\\s\\S])?+)*+\""));
    b.rule(SINGLE_QUOTED_STRING_LITERAL).is(SPACING, b.regexp("'([^'\\\\]*+(\\\\[\\s\\S])?+)*+'"));

    b.rule(NAME_LITERAL).is(SPACING, b.regexp("((::)?[a-z0-9][-\\\\w]*)(::[a-z0-9][-\\\\w]*)*"));

    b.rule(REF).is(SPACING, b.regexp("(::)?[A-Z]\\w*(::[A-Z]\\w*)*"));
    b.rule(VARIABLE_LITERAL).is(SPACING, b.regexp("\\$(::)?(\\w+::)*\\w+"));
    
    b.rule(NUMBER).is(SPACING, b.regexp("[-]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"));
    b.rule(STRING).is(SPACING, b.regexp("\"([^\"\\\\]*+(\\\\([\\\\\"/bfnrt]|u[0-9a-fA-F]{4}))?+)*+\""));
  }

}
