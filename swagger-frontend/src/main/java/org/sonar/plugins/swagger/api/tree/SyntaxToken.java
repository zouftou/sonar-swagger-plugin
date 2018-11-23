package org.sonar.plugins.swagger.api.tree;

public interface SyntaxToken extends Tree {

	String text();

	int line();

	int column();

	int endLine();

	int endColumn();

}
