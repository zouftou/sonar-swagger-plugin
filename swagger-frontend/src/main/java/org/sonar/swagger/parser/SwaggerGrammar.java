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
import org.sonar.plugins.swagger.api.tree.ArrayEntryTree;
import org.sonar.plugins.swagger.api.tree.ArrayTree;
import org.sonar.plugins.swagger.api.tree.EmptyArrayTree;
import org.sonar.plugins.swagger.api.tree.InternalArrayTree;
import org.sonar.plugins.swagger.api.tree.KeyTree;
import org.sonar.plugins.swagger.api.tree.LiteralTree;
import org.sonar.plugins.swagger.api.tree.ObjectEntryTree;
import org.sonar.plugins.swagger.api.tree.ObjectTree;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SimpleValueTree;
import org.sonar.plugins.swagger.api.tree.StringTree;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.tree.UnnamedObjectTree;
import org.sonar.plugins.swagger.api.tree.ValueTree;
import org.sonar.swagger.tree.impl.InternalSyntaxToken;
import org.sonar.swagger.tree.impl.SeparatedList;

import com.sonar.sslr.api.typed.GrammarBuilder;

public class SwaggerGrammar {

  private final GrammarBuilder<InternalSyntaxToken> b;
  private final TreeFactory f;

  public SwaggerGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
    this.b = b;
    this.f = f;
  }

  public SwaggerTree SWAGGER() {
    return b.<SwaggerTree>nonterminal(SwaggerLexicalGrammar.SWAGGER).is(
      f.swagger(
        b.optional(b.token(SwaggerLexicalGrammar.BOM)),
        b.optional(PAIR_LIST()),
        b.token(SwaggerLexicalGrammar.EOF)));
  }
  
  public SeparatedList<PairTree> PAIR_LIST() {
    return b.<SeparatedList<PairTree>>nonterminal().is(
      f.pairList(
        PAIR(),
        b.zeroOrMore(f.newTuple2(b.token(SwaggerLexicalGrammar.NEW_LINE), PAIR()))));
  }

  public ObjectTree OBJECT() {
    return b.<ObjectTree>nonterminal(SwaggerLexicalGrammar.OBJECT).is(
      f.object(
    	OBJECT_ENTRY(),
        b.zeroOrMore(f.newTuple2(b.token(SwaggerLexicalGrammar.NEW_LINE), OBJECT_ENTRY()))));
  }
  
  public ObjectEntryTree OBJECT_ENTRY() {
    return b.<ObjectEntryTree>nonterminal(SwaggerLexicalGrammar.OBJECT_ENTRY).is(
      f.objectEntry(
    	b.token(SwaggerLexicalGrammar.INDENTATION),
    	b.zeroOrMore(b.token(SwaggerLexicalGrammar.INDENTATION)),
    	PAIR()));
  }

  public ArrayTree ARRAY() {
    return b.<ArrayTree>nonterminal(SwaggerLexicalGrammar.ARRAY).is(
      f.array(
    	ARRAY_ENTRY(),
        b.zeroOrMore(f.newTuple1(b.token(SwaggerLexicalGrammar.NEW_LINE), ARRAY_ENTRY()))
      ));
  }
  
  public ArrayEntryTree ARRAY_ENTRY() {
    return b.<ArrayEntryTree>nonterminal(SwaggerLexicalGrammar.ARRAY_ENTRY).is(
      f.arrayEntry(
    	b.zeroOrMore(b.token(SwaggerLexicalGrammar.INDENTATION)),
		b.token(SwaggerLexicalGrammar.MINUS),
        b.firstOf(
          UNNAMED_OBJECT(),
          INTERNAL_ARRAY(),
          EMPTY_ARRAY(),
          VALUE_SIMPLE(),
          VALUE()
        )));
  }

  public PairTree PAIR() {
    return b.<PairTree>nonterminal(SwaggerLexicalGrammar.PAIR).is(
      f.pair(
        KEY(),
        b.token(SwaggerLexicalGrammar.COLON),
        b.firstOf(
          VALUE_SIMPLE(),
          VALUE()
        )));
  }

  public KeyTree KEY() {
    return b.<KeyTree>nonterminal(SwaggerLexicalGrammar.KEY).is(
      f.key(
	    b.firstOf(
	      b.token(SwaggerKeyword.HEADERS),
		  b.token(SwaggerKeyword.SWAGGER),
		  b.token(SwaggerKeyword.INFO),
		  b.token(SwaggerKeyword.TITLE),
		  b.token(SwaggerKeyword.DESCRIPTION),
		  b.token(SwaggerKeyword.TERMS_OF_SERVICE),
		  b.token(SwaggerKeyword.CONTACT),
		  b.token(SwaggerKeyword.NAME),
		  b.token(SwaggerKeyword.URL),
		  b.token(SwaggerKeyword.EMAIL),
		  b.token(SwaggerKeyword.LICENSE),
		  b.token(SwaggerKeyword.VERSION),
		  b.token(SwaggerKeyword.HOST),
		  b.token(SwaggerKeyword.BASE_PATH),
		  b.token(SwaggerKeyword.SCHEMES),
		  b.token(SwaggerKeyword.CONSUMES),
		  b.token(SwaggerKeyword.PRODUCES),
		  b.token(SwaggerKeyword.PATHS),
		  b.token(SwaggerKeyword.DEFINITIONS),
		  b.token(SwaggerKeyword.PARAMETERS),
		  b.token(SwaggerKeyword.RESPONSES),
		  b.token(SwaggerKeyword.SECURITY_DEFINITIONS),
		  b.token(SwaggerKeyword.SECURITY),
		  b.token(SwaggerKeyword.TAGS),
		  b.token(SwaggerKeyword.EXTERNAL_DOCS),
		  b.token(SwaggerKeyword.GET),
		  b.token(SwaggerKeyword.PUT),
		  b.token(SwaggerKeyword.POST),
		  b.token(SwaggerKeyword.DELETE),
		  b.token(SwaggerKeyword.OPTIONS),
		  b.token(SwaggerKeyword.HEAD),
		  b.token(SwaggerKeyword.PATCH),
		  b.token(SwaggerKeyword.SUMMARY),
		  b.token(SwaggerKeyword.OPERATION_ID),
		  b.token(SwaggerKeyword.DEPRECATED),
		  b.token(SwaggerKeyword.IN),
		  b.token(SwaggerKeyword.REQUIRED),
		  b.token(SwaggerKeyword.SCHEMA),
		  b.token(SwaggerKeyword.TYPE),
		  b.token(SwaggerKeyword.FORMAT),
		  b.token(SwaggerKeyword.ALLOW_EMPTY_VALUE),
		  b.token(SwaggerKeyword.ITEMS),
		  b.token(SwaggerKeyword.COLLECTION_FORMAT),
		  b.token(SwaggerKeyword.DEFAULT),
		  b.token(SwaggerKeyword.MAXIMUM),
		  b.token(SwaggerKeyword.EXCLUSIVE_MAXIMUM),
		  b.token(SwaggerKeyword.MINIMUM),
		  b.token(SwaggerKeyword.EXCLUSIVE_MINIMUM),
		  b.token(SwaggerKeyword.MAX_LENGTH),
		  b.token(SwaggerKeyword.MIN_LENGTH),
		  b.token(SwaggerKeyword.PATTERN),
		  b.token(SwaggerKeyword.MAX_ITEMS),
		  b.token(SwaggerKeyword.MIN_ITEMS),
		  b.token(SwaggerKeyword.UNIQUE_ITEMS),
		  b.token(SwaggerKeyword.ENUM),
		  b.token(SwaggerKeyword.MULTIPLE_OF),
		  
		  
		  b.token(SwaggerLexicalGrammar.SCOPE),
		  b.token(SwaggerLexicalGrammar.KEYWORD),
		  b.token(SwaggerLexicalGrammar.PATH),
		  b.token(SwaggerLexicalGrammar.REF),
		  
		  b.token(SwaggerLexicalGrammar.HTTP_STATUS)
	   )));
  }

  public ValueTree VALUE() {
    return b.<ValueTree>nonterminal(SwaggerLexicalGrammar.VALUE).is(
      f.value(
    	b.token(SwaggerLexicalGrammar.NEW_LINE),
        b.firstOf(
          OBJECT(),
          ARRAY()
        )));
  }
  
  public SimpleValueTree VALUE_SIMPLE() {
    return b.<SimpleValueTree>nonterminal(SwaggerLexicalGrammar.VALUE_SIMPLE).is(
      f.simpleValue(
    	b.token(SwaggerLexicalGrammar.SPACE),
        b.firstOf(
          TRUE(),
          FALSE(),
          NULL(),
          NUMBER(),
          DOUBLE_QUOTED_STRING(),
		  SINGLE_QUOTED_STRING(),
          STRING(),
          b.token(SwaggerLexicalGrammar.EMPTY_ARRAY_VALUE))
        ));
  }

  public UnnamedObjectTree UNNAMED_OBJECT() {
    return b.<UnnamedObjectTree>nonterminal(SwaggerLexicalGrammar.UNNAMED_OBJECT).is(
      f.unnamedObject(
        KEY(),
        b.token(SwaggerLexicalGrammar.COLON),
        VALUE_SIMPLE(),
        b.token(SwaggerLexicalGrammar.NEW_LINE),
        OBJECT()
      ));
  }
  
  public InternalArrayTree INTERNAL_ARRAY() {
    return b.<InternalArrayTree>nonterminal(SwaggerLexicalGrammar.INTERNAL_ARRAY).is(
      f.internalArray(
    	b.token(SwaggerLexicalGrammar.SPACE),
        KEY(),
        b.token(SwaggerLexicalGrammar.COLON),
        b.token(SwaggerLexicalGrammar.NEW_LINE),
        ARRAY()
      ));
  }
  
  public EmptyArrayTree EMPTY_ARRAY() {
    return b.<EmptyArrayTree>nonterminal(SwaggerLexicalGrammar.EMPTY_ARRAY).is(
      f.emptyArray(
    	b.token(SwaggerLexicalGrammar.SPACE),
        KEY(),
        b.token(SwaggerLexicalGrammar.COLON),
        b.token(SwaggerLexicalGrammar.SPACE),
        b.token(SwaggerLexicalGrammar.EMPTY_ARRAY_VALUE)
      ));
  }
  
  public StringTree STRING() {
    return b.<StringTree>nonterminal().is(
      f.string(b.token(SwaggerLexicalGrammar.STRING)));
  }

  public StringTree DOUBLE_QUOTED_STRING() {
    return b.<StringTree>nonterminal().is(
      f.string(b.token(SwaggerLexicalGrammar.DOUBLE_QUOTED_STRING)));
  }
  
  public StringTree SINGLE_QUOTED_STRING() {
    return b.<StringTree>nonterminal().is(
      f.string(b.token(SwaggerLexicalGrammar.SINGLE_QUOTED_STRING)));
  }
  
  public LiteralTree NUMBER() {
    return b.<LiteralTree>nonterminal().is(
      f.number(b.token(SwaggerLexicalGrammar.NUMBER)));
  }

  public LiteralTree FALSE() {
    return b.<LiteralTree>nonterminal().is(
      f.falsee(b.token(SwaggerLexicalGrammar.FALSE)));
  }

  public LiteralTree TRUE() {
    return b.<LiteralTree>nonterminal().is(
      f.truee(b.token(SwaggerLexicalGrammar.TRUE)));
  }

  public LiteralTree NULL() {
    return b.<LiteralTree>nonterminal().is(
      f.nul(b.token(SwaggerLexicalGrammar.NULL)));
  }
}
