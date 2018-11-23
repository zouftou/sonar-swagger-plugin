package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.*;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class EmptyArrayTreeImpl extends SWAGGERTree implements EmptyArrayTree {

	private final SyntaxToken space1;
	private final KeyTree key;
	private final SyntaxToken colon;
	private final SyntaxToken space2;
	private final SyntaxToken emptyArray;

	public EmptyArrayTreeImpl(SyntaxToken space1, KeyTree key, SyntaxToken colon, SyntaxToken space2,
			SyntaxToken emptyArray) {
		this.space1 = space1;
		this.key = key;
		this.colon = colon;
		this.space2 = space2;
		this.emptyArray = emptyArray;
	}

	@Override
	public Kind getKind() {
		return Kind.ARRAY_EMPTY;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.forArray(space1, key, colon, space2, emptyArray);
	}

	@Override
	public KeyTree key() {
		return key;
	}

	@Override
	public SyntaxToken colon() {
		return colon;
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitEmptyArray(this);
	}

	@Override
	public SyntaxToken space2() {
		return space2;
	}

	@Override
	public SyntaxToken space1() {
		return space1;
	}

	@Override
	public SyntaxToken emptyTree() {
		return emptyArray;
	}
}
