package org.sonar.plugins.swagger.api.tree;

import java.util.List;

import org.sonar.swagger.tree.impl.InternalSyntaxToken;

public interface ArrayEntryTree extends Tree {

	List<InternalSyntaxToken> indentations();

	SyntaxToken minus();

	Tree value();
}
