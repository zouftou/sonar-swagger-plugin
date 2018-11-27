/*
 * SonarQube Swagger Analyzer
 * Copyright (C) 2015-2017 Zouhir OUFTOU
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

import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.Optional;
import org.sonar.swagger.tree.impl.*;
import org.sonar.plugins.swagger.api.tree.*;

import java.util.List;

public class TreeFactory {

	public SwaggerTree swagger(Optional<SyntaxToken> byteOrderMark, Optional<SeparatedList<PairTree>> pairs,
			SyntaxToken eof) {
		return new SwaggerTreeImpl(byteOrderMark.orNull(), pairs.orNull(), eof);
	}

	public ObjectTree object(ObjectEntryTree entry,
			Optional<List<Tuple<InternalSyntaxToken, ObjectEntryTree>>> subsequentEntries) {
		List<ObjectEntryTree> entries = Lists.newArrayList();
		List<InternalSyntaxToken> indentations = Lists.newArrayList();

		entries.add(entry);

		if (subsequentEntries.isPresent()) {
			for (Tuple<InternalSyntaxToken, ObjectEntryTree> t : subsequentEntries.get()) {
				indentations.add(t.first());
				entries.add(t.second());
			}
		}

		return new ObjectTreeImpl(new SeparatedList<>(entries, indentations));
	}

	public ObjectEntryTree objectEntry(SyntaxToken indentation, Optional<List<InternalSyntaxToken>> indentations,
			PairTree pair) {
		return new ObjectEntryTreeImpl(indentation, indentations.orNull(), pair);
	}

	public ArrayTree array(ArrayEntryTree entry,
			Optional<List<Tuple<InternalSyntaxToken, ArrayEntryTree>>> subsequentEntries) {
		List<ArrayEntryTree> entries = Lists.newArrayList();
		List<InternalSyntaxToken> newLines = Lists.newArrayList();

		entries.add(entry);

		if (subsequentEntries.isPresent()) {
			for (Tuple<InternalSyntaxToken, ArrayEntryTree> t : subsequentEntries.get()) {
				newLines.add(t.first());
				entries.add(t.second());
			}
		}

		return new ArrayTreeImpl(new SeparatedList<>(entries, newLines));
	}

	public ArrayEntryTree arrayEntry(Optional<List<InternalSyntaxToken>> indentations, SyntaxToken minus, Tree value) {
		return new ArrayEntryTreeImpl(indentations.orNull(), minus, value);
	}

	public PairTree pair(KeyTree key, SyntaxToken colon, Tree value) {
		return new PairTreeImpl(key, colon, value);
	}

	public UnnamedObjectTree unnamedObject(KeyTree key, SyntaxToken colon, SimpleValueTree simpleValueTree,
			SyntaxToken newLine, ObjectTree objectTree) {
		return new UnnamedObjectTreeImpl(key, colon, simpleValueTree, newLine, objectTree);
	}

	public InternalArrayTree internalArray(SyntaxToken space, KeyTree key, SyntaxToken colon, SyntaxToken newLine,
			ArrayTree arrayTree) {
		return new InternalArrayTreeImpl(space, key, colon, newLine, arrayTree);
	}

	public EmptyArrayTree emptyArray(SyntaxToken space1, KeyTree key, SyntaxToken colon, SyntaxToken space2,
			SyntaxToken emptyTree) {
		return new EmptyArrayTreeImpl(space1, key, colon, space2, emptyTree);
	}

	public ValueTree value(SyntaxToken newLine, Tree value) {
		return new ValueTreeImpl(newLine, value);
	}

	public SimpleValueTree simpleValue(SyntaxToken space, Tree value) {
		return new SimpleValueTreeImpl(space, value);
	}

	public KeyTree key(SyntaxToken key) {
		return new KeyTreeImpl(key);
	}

	public SeparatedList<PairTree> pairList(PairTree pair,
			Optional<List<Tuple<InternalSyntaxToken, PairTree>>> subsequentPairs) {
		List<PairTree> pairs = Lists.newArrayList();
		List<InternalSyntaxToken> commas = Lists.newArrayList();

		pairs.add(pair);

		if (subsequentPairs.isPresent()) {
			for (Tuple<InternalSyntaxToken, PairTree> t : subsequentPairs.get()) {
				commas.add(t.first());
				pairs.add(t.second());
			}
		}

		return new SeparatedList<>(pairs, commas);
	}

	public StringTree string(SyntaxToken token) {
		return new StringTreeImpl(token);
	}

	public LiteralTree number(SyntaxToken token) {
		return new NumberTreeImpl(token);
	}

	public LiteralTree falsee(SyntaxToken token) {
		return new FalseTreeImpl(token);
	}

	public LiteralTree truee(SyntaxToken token) {
		return new TrueTreeImpl(token);
	}

	public LiteralTree nul(SyntaxToken token) {
		return new NullTreeImpl(token);
	}

	public static class Tuple<T, U> {

		private final T first;
		private final U second;

		public Tuple(T first, U second) {
			super();

			this.first = first;
			this.second = second;
		}

		public T first() {
			return first;
		}

		public U second() {
			return second;
		}
	}

	private static <T, U> Tuple<T, U> newTuple(T first, U second) {
		return new Tuple<>(first, second);
	}

	public <T, U> Tuple<T, U> newTuple1(T first, U second) {
		return newTuple(first, second);
	}

	public <T, U> Tuple<T, U> newTuple2(T first, U second) {
		return newTuple(first, second);
	}
}
