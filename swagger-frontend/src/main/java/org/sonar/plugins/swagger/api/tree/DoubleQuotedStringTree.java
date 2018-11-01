package org.sonar.plugins.swagger.api.tree;

public interface DoubleQuotedStringTree extends Tree {

  SyntaxToken leftDoubleQuote();
  
  StringTree text();
  
  SyntaxToken rightDoubleQuote();
}
