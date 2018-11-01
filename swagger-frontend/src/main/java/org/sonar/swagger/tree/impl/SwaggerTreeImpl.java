package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;
import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class SwaggerTreeImpl extends SWAGGERTree implements SwaggerTree {

  private final SyntaxToken byteOrderMark;
  private final SyntaxToken eof;
  private final ValueTree value;

  public SwaggerTreeImpl(@Nullable SyntaxToken byteOrderMark, @Nullable ValueTree value, SyntaxToken eof) {
    this.byteOrderMark = byteOrderMark;
    this.value = value;
    this.eof = eof;
  }

  @Override
  public Kind getKind() {
    return Kind.SWAGGER;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(byteOrderMark, value, eof);
  }

  @Override
  public boolean hasByteOrderMark() {
    return byteOrderMark != null;
  }

  @Override
  public ValueTree value() {
    return value;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitJson(this);
  }

}
