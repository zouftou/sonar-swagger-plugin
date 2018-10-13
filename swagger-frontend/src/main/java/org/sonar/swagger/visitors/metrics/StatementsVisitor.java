package org.sonar.swagger.visitors.metrics;

import com.google.common.collect.ImmutableList;

import java.util.List;

import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.SubscriptionVisitor;

public class StatementsVisitor extends SubscriptionVisitor {

	private int statements;

	public StatementsVisitor(Tree tree) {
		statements = 0;
		scanTree(tree);
	}

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.of(Tree.Kind.PAIR);
	}

	@Override
	public void visitNode(Tree tree) {
		statements++;
	}

	public int getNumberOfStatements() {
		return statements;
	}

}
