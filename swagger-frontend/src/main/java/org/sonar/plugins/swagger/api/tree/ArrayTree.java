package org.sonar.plugins.swagger.api.tree;

import java.util.List;

public interface ArrayTree extends Tree {

  SyntaxToken leftSpace();

  List<ValueTree> elements();

}