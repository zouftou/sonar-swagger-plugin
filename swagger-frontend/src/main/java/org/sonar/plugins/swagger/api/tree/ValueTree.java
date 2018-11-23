package org.sonar.plugins.swagger.api.tree;

public interface ValueTree extends Tree {

	SyntaxToken newLine();

	Tree value();

}
