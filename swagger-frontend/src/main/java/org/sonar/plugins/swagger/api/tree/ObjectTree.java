package org.sonar.plugins.swagger.api.tree;

import java.util.List;

public interface ObjectTree extends Tree {

  SyntaxToken leftSpace();

  List<PairTree> pairs();

}
