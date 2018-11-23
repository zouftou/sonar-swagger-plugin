package org.sonar.swagger.tree.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.ObjectEntryTree;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.collect.Iterators;

public class ObjectEntryTreeImpl extends SWAGGERTree implements ObjectEntryTree {

	private final SyntaxToken indentation;
	List<InternalSyntaxToken> indentations;
	private final PairTree pair;

	public ObjectEntryTreeImpl(SyntaxToken indentation, @Nullable List<InternalSyntaxToken> indentations,
			PairTree pair) {
		this.indentation = indentation;
		this.indentations = indentations;
		this.pair = pair;
	}

	@Override
	public Kind getKind() {
		return Kind.OBJECT_ENTRY;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.forArray(indentation, pair);
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitObjectEntry(this);
	}

	@Override
	public SyntaxToken indentation() {
		return indentation;
	}

	@Override
	public PairTree pair() {
		return pair;
	}

	@Override
	public List<InternalSyntaxToken> indentations() {
		return indentations;
	}
}
