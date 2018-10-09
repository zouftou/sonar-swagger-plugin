package org.sonar.swagger.visitors;

import java.io.File;

import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.visitors.TreeVisitorContext;

public class SwaggerVisitorContext implements TreeVisitorContext {

  private final SwaggerTree tree;
  private final File file;

  public SwaggerVisitorContext(SwaggerTree tree, File file) {
    this.tree = tree;
    this.file = file;
  }

  @Override
  public SwaggerTree getTopTree() {
    return tree;
  }

  @Override
  public File getFile() {
    return file;
  }

}