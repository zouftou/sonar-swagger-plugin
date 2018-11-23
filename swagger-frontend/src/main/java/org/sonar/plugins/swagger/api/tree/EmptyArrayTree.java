package org.sonar.plugins.swagger.api.tree;

public interface EmptyArrayTree extends Tree {

	SyntaxToken space1();

	KeyTree key();

	SyntaxToken colon();

	SyntaxToken space2();

	SyntaxToken emptyTree();

}
