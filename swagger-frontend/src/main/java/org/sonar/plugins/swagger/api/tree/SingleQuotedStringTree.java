package org.sonar.plugins.swagger.api.tree;

public interface SingleQuotedStringTree extends Tree {

  SyntaxToken leftSingleQuote();
  
  StringTree text();
  
  SyntaxToken rightSingleQuote();
}
