package org.sonar.swagger.parser;

import org.sonar.plugins.swagger.api.SwaggerKeyword;
import org.sonar.plugins.swagger.api.tree.ArrayTree;
import org.sonar.plugins.swagger.api.tree.DoubleQuotedStringTree;
import org.sonar.plugins.swagger.api.tree.KeyTree;
import org.sonar.plugins.swagger.api.tree.LiteralTree;
import org.sonar.plugins.swagger.api.tree.ObjectTree;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SingleQuotedStringTree;
import org.sonar.plugins.swagger.api.tree.StringTree;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
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
        b.optional(VALUE()),
        b.token(SwaggerLexicalGrammar.EOF)));
  }

  public ObjectTree OBJECT() {
    return b.<ObjectTree>nonterminal(SwaggerLexicalGrammar.OBJECT).is(
      f.object(
    	KEY(),
        b.token(SwaggerLexicalGrammar.COLON),
        b.optional(PAIR_LIST())));
  }

  public ArrayTree ARRAY() {
    return b.<ArrayTree>nonterminal(SwaggerLexicalGrammar.ARRAY).is(
      f.array(
        b.token(SwaggerLexicalGrammar.LEFT_BRACKET),
        b.optional(VALUE_LIST()),
        b.token(SwaggerLexicalGrammar.RIGHT_BRACKET)));
  }

  public SeparatedList<ValueTree> VALUE_LIST() {
    return b.<SeparatedList<ValueTree>>nonterminal().is(
      f.valueList(
        VALUE(),
        b.zeroOrMore(f.newTuple1(b.token(SwaggerLexicalGrammar.COMMA), VALUE()))));
  }

  public SeparatedList<PairTree> PAIR_LIST() {
    return b.<SeparatedList<PairTree>>nonterminal().is(
      f.pairList(
        PAIR(),
        b.zeroOrMore(f.newTuple2(b.token(SwaggerLexicalGrammar.COMMA), PAIR()))));
  }

  public PairTree PAIR() {
    return b.<PairTree>nonterminal(SwaggerLexicalGrammar.PAIR).is(
      f.pair(
        KEY(),
        b.token(SwaggerLexicalGrammar.COLON),
        VALUE()));
  }

  public KeyTree KEY() {
    return b.<KeyTree>nonterminal(SwaggerLexicalGrammar.KEY).is(
      f.key(
    	    b.firstOf(
    		  b.token(SwaggerKeyword.SWAGGER),
    		  b.token(SwaggerKeyword.INFO),
    		  b.token(SwaggerKeyword.TITLE)
    	   )));
  }

  public ValueTree VALUE() {
    return b.<ValueTree>nonterminal(SwaggerLexicalGrammar.VALUE).is(
      f.value(
        b.firstOf(
          OBJECT(),
          ARRAY(),
          TRUE(),
          FALSE(),
          NULL(),
          NUMBER(),
          DOUBLE_QUOTED_STRING(),
		  SINGLE_QUOTED_STRING(),
          STRING()
          )));
  }

  public StringTree STRING() {
    return b.<StringTree>nonterminal().is(
      f.string(b.token(SwaggerLexicalGrammar.STRING)));
  }
  
  public DoubleQuotedStringTree DOUBLE_QUOTED_STRING() {
    return b.<DoubleQuotedStringTree>nonterminal().is(
      f.doubleQuotedString(
    		  b.token(SwaggerLexicalGrammar.DOUBLE_QUOTE),
    		  STRING(),
    		  b.token(SwaggerLexicalGrammar.DOUBLE_QUOTE)));
  }
  
  
  public SingleQuotedStringTree SINGLE_QUOTED_STRING() {
    return b.<SingleQuotedStringTree>nonterminal().is(
      f.singleQuotedString(
    		  b.token(SwaggerLexicalGrammar.SINGLE_QUOTE),
    		  STRING(),
    		  b.token(SwaggerLexicalGrammar.SINGLE_QUOTE)));
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
