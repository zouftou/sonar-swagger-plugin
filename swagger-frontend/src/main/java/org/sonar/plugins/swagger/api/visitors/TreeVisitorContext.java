package org.sonar.plugins.swagger.api.visitors;

import java.io.File;

import org.sonar.plugins.swagger.api.tree.SwaggerTree;

public interface TreeVisitorContext {

  /**
   * @return the top tree node of the current file AST representation.
   */
  SwaggerTree getTopTree();

  /**
   * @return the current file
   */
  File getFile();

}
