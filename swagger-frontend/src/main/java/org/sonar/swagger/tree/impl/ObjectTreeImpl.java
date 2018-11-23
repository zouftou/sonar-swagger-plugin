package org.sonar.swagger.tree.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.sonar.plugins.swagger.api.tree.ObjectEntryTree;
import org.sonar.plugins.swagger.api.tree.ObjectTree;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.base.Functions;

public class ObjectTreeImpl extends SWAGGERTree implements ObjectTree {

	private final SeparatedList<ObjectEntryTree> entries;

	public ObjectTreeImpl(SeparatedList<ObjectEntryTree> entries) {
		this.entries = entries;
	}

	@Override
	public Kind getKind() {
		return Kind.OBJECT;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return entries != null ? entries.elementsAndSeparators(Functions.identity()) : new ArrayList<Tree>().iterator();
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitObject(this);
	}

	@Override
	public List<ObjectEntryTree> entries() {
		return entries != null ? entries : Collections.emptyList();
	}

}
