package org.sonar.plugins.swagger.api.tree;

import java.util.List;

public interface ObjectTree extends Tree {

  KeyTree key();
  
  SyntaxToken colon();

  List<PairTree> pairs();

}
