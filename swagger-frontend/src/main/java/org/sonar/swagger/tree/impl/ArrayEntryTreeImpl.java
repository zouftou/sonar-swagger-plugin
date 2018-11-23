package org.sonar.swagger.tree.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.ArrayEntryTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.collect.Iterators;

public class ArrayEntryTreeImpl extends SWAGGERTree implements ArrayEntryTree {

	private List<InternalSyntaxToken> indentations;
	private final SyntaxToken minus;
	private final Tree value;

	public ArrayEntryTreeImpl(@Nullable List<InternalSyntaxToken> indentations, SyntaxToken minus, Tree value) {
		this.indentations = indentations != null ? indentations : Collections.emptyList();
		this.minus = minus;
		this.value = value;
	}

	@Override
	public Kind getKind() {
		return Kind.ARRAY_ENTRY;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.concat(indentations.iterator(), Iterators.singletonIterator(minus),
				Iterators.singletonIterator(value));
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitArrayEntry(this);
	}

	@Override
	public SyntaxToken minus() {
		return minus;
	}

	@Override
	public Tree value() {
		return value;
	}

	@Override
	public List<InternalSyntaxToken> indentations() {
		return indentations;
	}
}
