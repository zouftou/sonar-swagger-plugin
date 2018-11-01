package org.sonar.swagger.tree.impl;

import com.google.common.base.Functions;
import com.google.common.collect.Iterators;
import org.sonar.plugins.swagger.api.tree.ArrayTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ArrayTreeImpl extends SWAGGERTree implements ArrayTree {

  private final SyntaxToken leftBracket;
  private final SeparatedList<ValueTree> values;
  private final SyntaxToken rightBracket;

  public ArrayTreeImpl(SyntaxToken leftBracket, @Nullable SeparatedList<ValueTree> values, SyntaxToken rightBracket) {
    this.leftBracket = leftBracket;
    this.values = values;
    this.rightBracket = rightBracket;
  }

  @Override
  public Kind getKind() {
    return Kind.ARRAY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(leftBracket),
      values != null ? values.elementsAndSeparators(Functions.identity()) : new ArrayList<Tree>().iterator(),
      Iterators.singletonIterator(rightBracket));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitArray(this);
  }

  @Override
  public SyntaxToken leftBracket() {
    return leftBracket;
  }

  @Override
  public SyntaxToken rightBracket() {
    return rightBracket;
  }

  @Override
  public List<ValueTree> elements() {
    return values != null ? values : Collections.emptyList();
  }

}
