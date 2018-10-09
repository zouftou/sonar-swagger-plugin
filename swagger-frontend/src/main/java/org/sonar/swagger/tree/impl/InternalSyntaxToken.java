package org.sonar.swagger.tree.impl;

import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;

public class InternalSyntaxToken extends SWAGGERTree implements SyntaxToken {

  private final int line;
  private final int column;
  private final String value;
  private final boolean isEOF;
  private final boolean isBOM;
  private int endLine;
  private int endColumn;

  public InternalSyntaxToken(int line, int column, String value, boolean isEOF, boolean isBOM) {
    this.value = value;
    this.line = line;
    this.column = column;
    this.isEOF = isEOF;
    this.isBOM = isBOM;
    calculateEndOffsets();
  }

  private void calculateEndOffsets() {
    String[] lines = value.split("\r\n|\n|\r", -1);
    endColumn = column + value.length();
    endLine = line + lines.length - 1;

    if (endLine != line) {
      endColumn = lines[lines.length - 1].length();
    }
  }

  @Override
  public int endLine() {
    return endLine;
  }

  @Override
  public int endColumn() {
    return endColumn;
  }

  @Override
  public int line() {
    return line;
  }

  @Override
  public int column() {
    return column;
  }

  @Override
  public Kind getKind() {
    return Kind.TOKEN;
  }

  @Override
  public boolean isLeaf() {
    return true;
  }

  public boolean isEOF() {
    return isEOF;
  }

  public boolean isBOM() {
    return isBOM;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitToken(this);
  }

  @Override
  public SyntaxToken getFirstToken() {
    return this;
  }

  @Override
  public SyntaxToken getLastToken() {
    return this;
  }

  @Override
  public String text() {
    return value;
  }

}
