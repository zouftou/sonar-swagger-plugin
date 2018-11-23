package org.sonar.plugins.swagger.api.tree;

public interface SimpleValueTree extends Tree {

	SyntaxToken space();

	Tree value();

}
