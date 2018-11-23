package org.sonar.plugins.swagger.api.tree;

public interface InternalArrayTree extends Tree {

	SyntaxToken space();

	KeyTree key();

	SyntaxToken colon();

	SyntaxToken newLine();

	ArrayTree arrayTree();

}
