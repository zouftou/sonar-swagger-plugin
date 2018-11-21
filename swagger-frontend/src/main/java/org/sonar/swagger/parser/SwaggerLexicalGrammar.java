/*
 * SonarQube Swagger Analyzer
 * Copyright (C) 2018-2020 Zouhir OUFTOU
 * zouhir.ouftou@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
  HTTP_STATUS,
  REF,
  SCOPE,
  
  KEYWORD,
  PAIR,
  KEY,
  
  ARRAY,
  ARRAY_ENTRY,
  INTERNAL_ARRAY,
  EMPTY_ARRAY,
  EMPTY_ARRAY_VALUE,
  
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
  SPACE,
  INDENTATION,
  NEW_LINE,

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
	
    b.rule(COLON).is(":");
    b.rule(MINUS).is("-");
    b.rule(SPACE).is(" ");
    b.rule(INDENTATION).is("  ");
    b.rule(REF).is("$ref");
    b.rule(NEW_LINE).is(b.regexp("[\r]?\n"));// \n for unix, \r\n for windows
    b.rule(EMPTY_ARRAY_VALUE).is("[]");
    
    b.rule(BOM).is("\ufeff");
    b.rule(EOF).is(SPACING,b.token(GenericTokenType.EOF, b.endOfInput()));
  }

  private static void types(LexerlessGrammarBuilder b) {
    b.rule(TRUE).is(SPACING,"true");
    b.rule(FALSE).is(SPACING,"false");
    b.rule(NULL).is(SPACING,"null");

    b.rule(NUMBER).is(SPACING,b.regexp("[-]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"));

    b.rule(KEYWORD).is(SPACING,b.regexp("[\\w\\-]+"));
    
    b.rule(STRING).is(b.regexp("\\w.*"));
    b.rule(DOUBLE_QUOTED_STRING).is(b.regexp("\"([^\"\\\\]*+(\\\\([\\\\\"/bfnrt]|u[0-9a-fA-F]{4}))?+)*+\""));
    b.rule(SINGLE_QUOTED_STRING).is(b.regexp("\'([^'\\\\]*+(\\\\([\\\\\"/bfnrt]|u[0-9a-fA-F]{4}))?+)*+'"));
    
    // ex: write:pets
    b.rule(SCOPE).is(SPACING,b.regexp("[\\w]+:[\\w]+"));
    b.rule(PATH).is(SPACING,b.regexp("(/[a-zA-Z0-9_-{}]+)+"));
    b.rule(HTTP_STATUS).is(SPACING,b.regexp("[1-5][0-9][0-9]"));

    b.rule(SPACING).is(b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+")));
  }
  
  private static void keywords(LexerlessGrammarBuilder b) {
    for (SwaggerKeyword tokenType : SwaggerKeyword.values()) {
        b.rule(tokenType).is(tokenType.getValue());
    }
  }

}
