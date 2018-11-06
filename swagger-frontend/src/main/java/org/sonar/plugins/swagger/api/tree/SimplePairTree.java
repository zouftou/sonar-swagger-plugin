package org.sonar.plugins.swagger.api.tree;

public interface SimplePairTree extends Tree {
  
  KeyTree key();

  SyntaxToken colon();
  
  SyntaxToken whiteSpace();
  
  LiteralTree value();

}
