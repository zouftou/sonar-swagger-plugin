package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.*;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class UnnamedObjectTreeImpl extends SWAGGERTree implements UnnamedObjectTree {

	private final KeyTree key;
	private final SyntaxToken colon;
	private final SimpleValueTree simpleValueTree;
	private final SyntaxToken newLine;
	private final ObjectTree objectTree;

	public UnnamedObjectTreeImpl(KeyTree key, SyntaxToken colon, SimpleValueTree simpleValueTree, SyntaxToken newLine,
			ObjectTree objectTree) {
		this.key = key;
		this.colon = colon;
		this.simpleValueTree = simpleValueTree;
		this.newLine = newLine;
		this.objectTree = objectTree;
	}

	@Override
	public Kind getKind() {
		return Kind.OBJECT_UNNAMED;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.forArray(key, colon, simpleValueTree, newLine, objectTree);
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
		visitor.visitUnnamedObject(this);
	}

	@Override
	public SimpleValueTree simpleValueTree() {
		return simpleValueTree;
	}

	@Override
	public SyntaxToken newLine() {
		return newLine;
	}

	@Override
	public ObjectTree objectTree() {
		return objectTree;
	}
}
