package org.sonar.swagger.tree.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.sonar.plugins.swagger.api.tree.ArrayEntryTree;
import org.sonar.plugins.swagger.api.tree.ArrayTree;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.base.Functions;

public class ArrayTreeImpl extends SWAGGERTree implements ArrayTree {

	private final SeparatedList<ArrayEntryTree> entries;

	public ArrayTreeImpl(SeparatedList<ArrayEntryTree> entries) {
		this.entries = entries;
	}

	@Override
	public Kind getKind() {
		return Kind.ARRAY;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return entries != null ? entries.elementsAndSeparators(Functions.identity()) : new ArrayList<Tree>().iterator();
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitArray(this);
	}

	@Override
	public List<ArrayEntryTree> entries() {
		return entries != null ? entries : Collections.emptyList();
	}

}
