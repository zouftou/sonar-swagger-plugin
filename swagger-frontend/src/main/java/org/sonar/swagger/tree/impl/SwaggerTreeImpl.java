package org.sonar.swagger.tree.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.base.Functions;
import com.google.common.collect.Iterators;

public class SwaggerTreeImpl extends SWAGGERTree implements SwaggerTree {

	private final SyntaxToken byteOrderMark;
	private final SyntaxToken eof;
	private final SeparatedList<PairTree> pairs;

	public SwaggerTreeImpl(@Nullable SyntaxToken byteOrderMark, @Nullable SeparatedList<PairTree> pairs,
			SyntaxToken eof) {
		this.byteOrderMark = byteOrderMark;
		this.pairs = pairs;
		this.eof = eof;
	}

	@Override
	public Kind getKind() {
		return Kind.SWAGGER;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.concat(Iterators.singletonIterator(byteOrderMark),
				pairs != null ? pairs.elementsAndSeparators(Functions.identity()) : new ArrayList<Tree>().iterator(),
				Iterators.singletonIterator(eof));
	}

	@Override
	public boolean hasByteOrderMark() {
		return byteOrderMark != null;
	}

	@Override
	public List<PairTree> pairs() {
		return pairs;
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitSwagger(this);
	}

}
