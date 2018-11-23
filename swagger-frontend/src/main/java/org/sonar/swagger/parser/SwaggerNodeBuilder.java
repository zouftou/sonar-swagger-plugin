package org.sonar.swagger.parser;

import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Rule;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.api.Trivia;
import com.sonar.sslr.api.typed.Input;
import com.sonar.sslr.api.typed.NodeBuilder;

import java.util.List;

import org.sonar.swagger.tree.impl.InternalSyntaxSpacing;
import org.sonar.swagger.tree.impl.InternalSyntaxToken;
import org.sonar.sslr.grammar.GrammarRuleKey;

public class SwaggerNodeBuilder implements NodeBuilder {

	private static final char BYTE_ORDER_MARK = '\uFEFF';

	@Override
	public Object createNonTerminal(GrammarRuleKey ruleKey, Rule rule, List<Object> children, int startIndex,
			int endIndex) {
		for (Object child : children) {
			if (child instanceof InternalSyntaxToken) {
				return child;
			}
		}
		return new InternalSyntaxSpacing(startIndex, endIndex);
	}

	@Override
	public Object createTerminal(Input input, int startIndex, int endIndex, List<Trivia> trivias, TokenType type) {
		char[] fileChars = input.input();
		boolean hasByteOrderMark = fileChars.length > 0 && fileChars[0] == BYTE_ORDER_MARK;
		boolean isEof = GenericTokenType.EOF.equals(type);
		LineColumnValue lineColumnValue = tokenPosition(input, startIndex, endIndex);
		return new InternalSyntaxToken(lineColumnValue.line,
				column(hasByteOrderMark, lineColumnValue.line, lineColumnValue.column), lineColumnValue.value, isEof,
				isByteOrderMark(input, startIndex, endIndex));
	}

	private static int column(boolean hasByteOrderMark, int line, int column) {
		if (hasByteOrderMark && line == 1) {
			return column - 1;
		}
		return column;
	}

	private static LineColumnValue tokenPosition(Input input, int startIndex, int endIndex) {
		int[] lineAndColumn = input.lineAndColumnAt(startIndex);
		String value = input.substring(startIndex, endIndex);
		return new LineColumnValue(lineAndColumn[0], lineAndColumn[1] - 1, value);
	}

	private static boolean isByteOrderMark(Input input, int startIndex, int endIndex) {
		return (Character.toString(BYTE_ORDER_MARK)).equals(input.substring(startIndex, endIndex));
	}

	private static class LineColumnValue {
		final int line;
		final int column;
		final String value;

		private LineColumnValue(int line, int column, String value) {
			this.line = line;
			this.column = column;
			this.value = value;
		}
	}

}
