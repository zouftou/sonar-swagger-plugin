package org.sonar.swagger.parser;

import java.util.List;

import org.sonar.plugins.swagger.api.SwaggerKeyword;
import org.sonar.plugins.swagger.api.tree.ArrayEntryTree;
import org.sonar.plugins.swagger.api.tree.ArrayTree;
import org.sonar.plugins.swagger.api.tree.KeyTree;
import org.sonar.plugins.swagger.api.tree.LiteralTree;
import org.sonar.plugins.swagger.api.tree.ObjectTree;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SimplePairTree;
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
        b.optional(VALUE_LIST()),
        b.token(SwaggerLexicalGrammar.EOF)));
  }
  
  public SeparatedList<ValueTree> VALUE_LIST() {
    return b.<SeparatedList<ValueTree>>nonterminal().is(
      f.valueList(
    	VALUE(),
        b.zeroOrMore(f.newTuple2(b.token(SwaggerLexicalGrammar.NEW_LINE), VALUE()))));
  }
  
  public ObjectTree OBJECT() {
    return b.<ObjectTree>nonterminal(SwaggerLexicalGrammar.OBJECT).is(
      f.object(
    	KEY(),
        b.token(SwaggerLexicalGrammar.COLON),
        b.token(SwaggerLexicalGrammar.NEW_LINE),
        PAIR_LIST()));
  }

  public ArrayTree ARRAY() {
    return b.<ArrayTree>nonterminal(SwaggerLexicalGrammar.ARRAY).is(
      f.array(
    	KEY(),
        b.token(SwaggerLexicalGrammar.COLON),
        b.token(SwaggerLexicalGrammar.NEW_LINE),
        ENTRY_LIST()));
  }
  
  public SimplePairTree KEY_VALUE() {
    return b.<SimplePairTree>nonterminal(SwaggerLexicalGrammar.SIMPLE_PAIR).is(
      f.simplePair(
        KEY(),
        b.token(SwaggerLexicalGrammar.COLON),
        b.token(SwaggerLexicalGrammar.WHITESPACE),
        b.firstOf(
            TRUE(),
            FALSE(),
            NULL(),
            NUMBER(),
            DOUBLE_QUOTED_STRING(),
  		    SINGLE_QUOTED_STRING(),
            STRING())
        ));
  }

  public List<ArrayEntryTree> ENTRY_LIST() {
    return b.<List<ArrayEntryTree>>nonterminal().is(
      f.entryList(
    	ENTRY(),
        b.zeroOrMore(f.newTuple2(b.token(SwaggerLexicalGrammar.NEW_LINE), ENTRY()))));
  }
  
  public ArrayEntryTree ENTRY() {
    return b.<ArrayEntryTree>nonterminal(SwaggerLexicalGrammar.ARRAY_ENTRY).is(
      f.arrayEntry(
    	b.token(SwaggerLexicalGrammar.MINUS),
    	b.token(SwaggerLexicalGrammar.WHITESPACE),
        VALUE()));
  }
  
  public SeparatedList<PairTree> PAIR_LIST() {
    return b.<SeparatedList<PairTree>>nonterminal().is(
      f.pairList(
        PAIR(),
        b.zeroOrMore(f.newTuple2(b.token(SwaggerLexicalGrammar.NEW_LINE), PAIR()))));
  }

  public PairTree PAIR() {
    return b.<PairTree>nonterminal(SwaggerLexicalGrammar.PAIR).is(
      f.pair(
        b.token(SwaggerLexicalGrammar.INDENTATION),
        KEY(),
        b.token(SwaggerLexicalGrammar.COLON),
        b.token(SwaggerLexicalGrammar.WHITESPACE),
        VALUE()));
  }

  public KeyTree KEY() {
    return b.<KeyTree>nonterminal(SwaggerLexicalGrammar.KEY).is(
      f.key(
    	    b.firstOf(
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
    		  b.token(SwaggerKeyword.PRODUCES),
    		  b.token(SwaggerKeyword.PATHS),
    		  b.token(SwaggerKeyword.DEFINITIONS),
    		  b.token(SwaggerKeyword.PARAMETERS),
    		  b.token(SwaggerKeyword.RESPONSES),
    		  b.token(SwaggerKeyword.SECURITY_DEFINITIONS),
    		  b.token(SwaggerKeyword.SECURITY),
    		  b.token(SwaggerKeyword.TAGS),
    		  b.token(SwaggerKeyword.EXTERNAL_DOCS)
    	   )));
  }

  public ValueTree VALUE() {
    return b.<ValueTree>nonterminal(SwaggerLexicalGrammar.VALUE).is(
      f.value(
        b.firstOf(
          OBJECT(),
          ARRAY(),
          KEY_VALUE(),
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
