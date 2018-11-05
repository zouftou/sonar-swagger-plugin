package org.sonar.plugins.swagger.api.tree;

import java.util.List;

public interface ObjectTree extends Tree {

  KeyTree key();
  
  SyntaxToken colon();
  
  SyntaxToken newLine();
  
  List<PairTree> pairs();

}
