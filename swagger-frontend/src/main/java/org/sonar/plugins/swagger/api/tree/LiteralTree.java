package org.sonar.plugins.swagger.api.tree;

public interface LiteralTree extends Tree {

  SyntaxToken value();

  String text();

}
