package org.sonar.plugins.swagger.api.tree;

import java.util.List;

public interface ArrayTree extends Tree {

  SyntaxToken leftBracket();

  List<ValueTree> elements();

  SyntaxToken rightBracket();

}
