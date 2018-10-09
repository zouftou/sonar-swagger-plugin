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

  private final SyntaxToken leftSpace;
  private final SeparatedList<ValueTree> values;

  public ArrayTreeImpl(SyntaxToken leftSpace, @Nullable SeparatedList<ValueTree> values) {
    this.leftSpace = leftSpace;
    this.values = values;
  }

  @Override
  public Kind getKind() {
    return Kind.ARRAY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(leftSpace),
      values != null ? values.elementsAndSeparators(Functions.identity()) : new ArrayList<Tree>().iterator());
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitArray(this);
  }

  @Override
  public SyntaxToken leftSpace() {
    return leftSpace;
  }

  @Override
  public List<ValueTree> elements() {
    return values != null ? values : Collections.emptyList();
  }

}
