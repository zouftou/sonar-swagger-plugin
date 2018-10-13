package org.sonar.swagger.visitors.metrics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import org.sonar.swagger.tree.impl.InternalSyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.SubscriptionVisitor;

public class LinesOfCodeVisitor extends SubscriptionVisitor {

	private Set<Integer> linesOfCode = Sets.newHashSet();

	public LinesOfCodeVisitor(Tree tree) {
		linesOfCode.clear();
		scanTree(tree);
	}

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.of(Tree.Kind.TOKEN);
	}

	@Override
	public void visitNode(Tree tree) {
		InternalSyntaxToken token = (InternalSyntaxToken) tree;
		if (!token.isEOF() && !token.isBOM()) {
			linesOfCode.add(token.line());
		}
	}

	public int getNumberOfLinesOfCode() {
		return linesOfCode.size();
	}

}
