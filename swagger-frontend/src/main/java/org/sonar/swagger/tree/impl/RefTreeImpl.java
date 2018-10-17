package org.sonar.swagger.tree.impl;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.LiteralTree;
import org.sonar.plugins.swagger.api.tree.RefTree;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class RefTreeImpl extends SWAGGERTree implements RefTree {

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Kind getKind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
