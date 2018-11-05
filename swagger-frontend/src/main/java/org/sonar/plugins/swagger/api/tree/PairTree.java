package org.sonar.plugins.swagger.api.tree;

public interface PairTree extends Tree {

  SyntaxToken indentation();
  
  KeyTree key();

  SyntaxToken colon();
  
  SyntaxToken whiteSpace();
  
  ValueTree value();

}
