package org.sonar.swagger.parser;

import org.sonar.plugins.swagger.api.SwaggerKeyword;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

import com.sonar.sslr.api.GenericTokenType;

public enum SwaggerLexicalGrammar implements GrammarRuleKey {

	SWAGGER,

	OBJECT, OBJECT_ENTRY, OBJECT_UNNAMED,

	ARRAY, ARRAY_ENTRY, ARRAY_INTERNAL, ARRAY_EMPTY, ARRAY_EMPTY_VALUE,
	
	PAIR,
	
	KEY, KEYWORD,
	
	VALUE, VALUE_SIMPLE,

	STRING, STRING_SINGLE_QUOTED, STRING_DOUBLE_QUOTED,
	
	NUMBER,

	PATH, HTTP_STATUS, REF, SCOPE,
	
	TRUE, FALSE, NULL,

	COLON, MINUS, SPACE, INDENTATION, NEW_LINE,

	BOM, EOF,

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
		b.rule(ARRAY_EMPTY_VALUE).is("[]");

		b.rule(BOM).is("\ufeff");
		b.rule(EOF).is(SPACING, b.token(GenericTokenType.EOF, b.endOfInput()));
	}

	private static void types(LexerlessGrammarBuilder b) {
		b.rule(TRUE).is(SPACING, "true");
		b.rule(FALSE).is(SPACING, "false");
		b.rule(NULL).is(SPACING, "null");

		b.rule(NUMBER).is(SPACING, b.regexp("[-]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?"));

		b.rule(KEYWORD).is(SPACING, b.regexp("[\\w\\-]+"));

		b.rule(STRING).is(b.regexp("\\w.*"));
		b.rule(STRING_DOUBLE_QUOTED).is(b.regexp("\"([^\"\\\\]*+(\\\\([\\\\\"/bfnrt]|u[0-9a-fA-F]{4}))?+)*+\""));
		b.rule(STRING_SINGLE_QUOTED).is(b.regexp("\'([^'\\\\]*+(\\\\([\\\\\"/bfnrt]|u[0-9a-fA-F]{4}))?+)*+'"));

		// ex: write:pets
		b.rule(SCOPE).is(SPACING, b.regexp("[\\w]+:[\\w]+"));
		b.rule(PATH).is(SPACING, b.regexp("(/[a-zA-Z0-9_-{}]+)+"));
		b.rule(HTTP_STATUS).is(SPACING, b.regexp("[1-5][0-9][0-9]"));

		b.rule(SPACING).is(b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+")));
	}

	private static void keywords(LexerlessGrammarBuilder b) {
		for (SwaggerKeyword tokenType : SwaggerKeyword.values()) {
			b.rule(tokenType).is(tokenType.getValue());
		}
	}

}
