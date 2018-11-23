package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.*;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class PairTreeImpl extends SWAGGERTree implements PairTree {

	private final KeyTree key;
	private final SyntaxToken colon;
	private final Tree value;

	public PairTreeImpl(KeyTree key, SyntaxToken colon, Tree value) {
		this.key = key;
		this.colon = colon;
		this.value = value;
	}

	@Override
	public Kind getKind() {
		return Kind.PAIR;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.forArray(key, colon, value);
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
	public Tree value() {
		return value;
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitPair(this);
	}
}
