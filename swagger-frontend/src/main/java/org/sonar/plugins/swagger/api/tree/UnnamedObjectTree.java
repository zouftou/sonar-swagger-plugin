package org.sonar.plugins.swagger.api.tree;

public interface UnnamedObjectTree extends Tree {

	KeyTree key();

	SyntaxToken colon();

	SimpleValueTree simpleValueTree();

	SyntaxToken newLine();

	ObjectTree objectTree();

}
