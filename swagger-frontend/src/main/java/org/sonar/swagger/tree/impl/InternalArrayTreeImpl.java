package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.*;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class InternalArrayTreeImpl extends SWAGGERTree implements InternalArrayTree {

	private final SyntaxToken space;
	private final KeyTree key;
	private final SyntaxToken colon;
	private final SyntaxToken newLine;
	private final ArrayTree arrayTree;

	public InternalArrayTreeImpl(SyntaxToken space, KeyTree key, SyntaxToken colon, SyntaxToken newLine,
			ArrayTree arrayTree) {
		this.space = space;
		this.key = key;
		this.colon = colon;
		this.newLine = newLine;
		this.arrayTree = arrayTree;
	}

	@Override
	public Kind getKind() {
		return Kind.ARRAY_INTERNAL;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.forArray(space, key, colon, newLine, arrayTree);
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
		visitor.visitInternalArray(this);
	}

	@Override
	public SyntaxToken newLine() {
		return newLine;
	}

	@Override
	public ArrayTree arrayTree() {
		return arrayTree;
	}

	@Override
	public SyntaxToken space() {
		return space;
	}
}
