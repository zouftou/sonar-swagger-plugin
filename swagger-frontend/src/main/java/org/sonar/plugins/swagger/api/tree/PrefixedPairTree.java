package org.sonar.plugins.swagger.api.tree;

public interface PrefixedPairTree extends Tree {

  SyntaxToken doubleWhiteSpace();
  
  KeyTree key();

  SyntaxToken colon();
  
  ValueTree value();

}
