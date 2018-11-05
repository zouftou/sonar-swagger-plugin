package org.sonar.plugins.swagger.api.tree;

public interface ArrayEntryTree extends Tree {

  SyntaxToken minus();

  SyntaxToken whitespace();
  
  ValueTree value();
}
