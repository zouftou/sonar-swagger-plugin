package org.sonar.swagger.tree.impl;

import com.google.common.base.Functions;
import com.google.common.collect.Iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class SwaggerTreeImpl extends SWAGGERTree implements SwaggerTree {

  private final SyntaxToken byteOrderMark;
  private final SyntaxToken eof;
  private final SeparatedList<ValueTree> values;

  public SwaggerTreeImpl(@Nullable SyntaxToken byteOrderMark, @Nullable SeparatedList<ValueTree> values, SyntaxToken eof) {
    this.byteOrderMark = byteOrderMark;
    this.values = values;
    this.eof = eof;
  }

  @Override
  public Kind getKind() {
    return Kind.SWAGGER;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
	return Iterators.concat(
    Iterators.singletonIterator(byteOrderMark),
    values != null ? values.elementsAndSeparators(Functions.identity()) : new ArrayList<Tree>().iterator(),
    Iterators.singletonIterator(eof));
  }

  @Override
  public boolean hasByteOrderMark() {
    return byteOrderMark != null;
  }

  @Override
  public List<ValueTree> values() {
    return values;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitSwagger(this);
  }

}
