package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.KeyTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class KeyTreeImpl extends SWAGGERTree implements KeyTree {

	private final SyntaxToken key;

	public KeyTreeImpl(SyntaxToken key) {
		this.key = key;
	}

	@Override
	public Tree.Kind getKind() {
		return Tree.Kind.KEY;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.singletonIterator(key);
	}

	@Override
	public String actualText() {
		if (key.text().startsWith("\"") || key.text().startsWith("'")) {
			return key.text().substring(1, key.text().length() - 1);
		}
		return key.text();
	}

	@Override
	public SyntaxToken value() {
		return key;
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitKey(this);
	}

}
