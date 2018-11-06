package org.sonar.plugins.swagger.api.tree;

import java.util.List;

public interface UnnamedObjectTree extends Tree {

  SimplePairTree simplePaire();
  
  List<PairTree> pairs();
  
  SyntaxToken newLine();

}
