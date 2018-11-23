package org.sonar.plugins.swagger.api.tree;

public interface PairTree extends Tree {

	KeyTree key();

	SyntaxToken colon();

	Tree value();

}
