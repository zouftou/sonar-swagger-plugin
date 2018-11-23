package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.LiteralTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;

public abstract class LiteralTreeImpl extends SWAGGERTree implements LiteralTree {

	private final SyntaxToken value;
	private final String text;

	public LiteralTreeImpl(SyntaxToken value) {
		this.value = value;
		text = value.text();
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.singletonIterator(value);
	}

	@Override
	public SyntaxToken value() {
		return value;
	}

	@Override
	public String text() {
		return text;
	}

}
