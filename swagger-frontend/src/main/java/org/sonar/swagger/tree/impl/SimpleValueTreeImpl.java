package org.sonar.swagger.tree.impl;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.SimpleValueTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.collect.Iterators;

public class SimpleValueTreeImpl extends SWAGGERTree implements SimpleValueTree {

	private final SyntaxToken space;
	private final Tree value;

	public SimpleValueTreeImpl(SyntaxToken space, Tree value) {
		this.space = space;
		this.value = value;
	}

	@Override
	public Kind getKind() {
		return Kind.VALUE_SIMPLE;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.singletonIterator(value);
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitSimpleValue(this);
	}

	@Override
	public Tree value() {
		return value;
	}

	@Override
	public SyntaxToken space() {
		return space;
	}
}
