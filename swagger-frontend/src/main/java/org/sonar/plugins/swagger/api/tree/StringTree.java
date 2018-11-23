package org.sonar.plugins.swagger.api.tree;

public interface StringTree extends LiteralTree {

	/**
	 * @return String without double quotes
	 */
	String actualText();

}
