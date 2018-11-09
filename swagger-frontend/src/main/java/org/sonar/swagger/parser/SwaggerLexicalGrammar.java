package org.sonar.swagger.parser;

import org.sonar.plugins.swagger.api.SwaggerKeyword;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import com.sonar.sslr.api.GenericTokenType;

public enum SwaggerLexicalGrammar implements GrammarRuleKey {

  SWAGGER,
  
  OBJECT,
  OBJECT_ENTRY,
  UNNAMED_OBJECT,
  
  PATH,
  REF,
  
  PAIR,
  SIMPLE_PAIR,
  PREFIXED_PAIR,
  KEY,
  KEY_VALUE,
  SIMPLE_VALUE,
  
  ARRAY,
  ARRAY_ENTRY,
  
  VALUE,
  VALUE_SIMPLE,
  
  TRUE,
  FALSE,
  NULL,

  STRING,
  SINGLE_QUOTED_STRING,
  DOUBLE_QUOTED_STRING,
  NUMBER,

  COLON,
  MINUS,
  WHITESPACE,
  INDENTATION,
  NEW_LINE,

  BOM,
  EOF,
  
  SPACING;
  //https://github.com/atom/language-yaml/blob/master/grammars/yaml.cson
  public static LexerlessGrammarBuilder createGrammar() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    tokens(b);
    types(b);
    keywords(b);
    b.setRootRule(SWAGGER);
    return b;
  }
  
  private static void tokens(LexerlessGrammarBuilder b) {
	
    b.rule(COLON).is(":");
    b.rule(MINUS).is("-");
    b.rule(WHITESPACE).is(" ");
    b.rule(INDENTATION).is("  ");
    b.rule(REF).is("$ref");
    b.rule(NEW_LINE).is(System.getProperty("line.separator"));// \n for unix, \r\n for windows

    b.rule(BOM).is("\ufeff");
    b.rule(EOF).is(SPACING, b.token(GenericTokenType.EOF, b.endOfInput()));
  }

  private static void types(LexerlessGrammarBuilder b) {
    b.rule(TRUE).is(SPACING, "true");
    b.rule(FALSE).is(SPACING, "false");
    b.rule(NULL).is(SPACING, "null");

    b.rule(NUMBER).is(SPACING, b.regexp("[-]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"));
    
    b.rule(STRING).is(b.regexp("[^\\s\"':\\-\\n](?!\\s*#(?!\\{))([^#\\n]|((?<!\\s)#))*"));
    b.rule(DOUBLE_QUOTED_STRING).is(b.regexp("\"([^\\s\"'\\n](?!\\s*#(?!\\{))([^#\\n]|((?<!\\s)#))*)+\""));
    b.rule(SINGLE_QUOTED_STRING).is(b.regexp("\'([^\\s\"'\\n](?!\\s*#(?!\\{))([^#\\n]|((?<!\\s)#))*)+\'"));
    
    //(/[a-zA-Z0-9_-]+)+/?
    b.rule(PATH).is(b.regexp("(/[a-zA-Z0-9_-]+)+/?"));

    b.rule(SPACING).is(b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+")));
  }
  
  private static void keywords(LexerlessGrammarBuilder b) {
    for (SwaggerKeyword tokenType : SwaggerKeyword.values()) {
        b.rule(tokenType).is(SPACING, tokenType.getValue());
    }
  }

}
