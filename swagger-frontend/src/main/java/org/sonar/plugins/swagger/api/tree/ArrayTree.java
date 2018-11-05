package org.sonar.plugins.swagger.api.tree;

import java.util.List;

public interface ArrayTree extends Tree {

  KeyTree key();
  
  SyntaxToken colon();
  
  SyntaxToken newLine();

  List<ArrayEntryTree> elements();

}
