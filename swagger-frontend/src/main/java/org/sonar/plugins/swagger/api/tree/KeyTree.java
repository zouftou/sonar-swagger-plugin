package org.sonar.plugins.swagger.api.tree;

public interface KeyTree extends Tree {

  SyntaxToken value();

  String actualText();

}
