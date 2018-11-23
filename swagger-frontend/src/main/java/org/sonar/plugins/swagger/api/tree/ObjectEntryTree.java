package org.sonar.plugins.swagger.api.tree;

import java.util.List;

import org.sonar.swagger.tree.impl.InternalSyntaxToken;

public interface ObjectEntryTree extends Tree {

	SyntaxToken indentation();

	List<InternalSyntaxToken> indentations();

	PairTree pair();

}
