package org.sonar.swagger.tree.impl;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.SyntaxSpacing;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class InternalSyntaxSpacing extends SWAGGERTree implements SyntaxSpacing {

  public InternalSyntaxSpacing(int startIndex, int endIndex) {
  }

  @Override
  public Kind getKind() {
    return Kind.SPACING;
  }

  @Override
  public boolean isLeaf() {
    return true;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    // Do nothing at the moment. Spacings are dropped anyway.
  }

  @Override
  public int column() {
    return 0;
  }

  @Override
  public int line() {
    return 0;
  }

  @Override
  public int endColumn() {
    return 0;
  }

  @Override
  public int endLine() {
    return 0;
  }

  @Override
  public String text() {
    return "";
  }

}
