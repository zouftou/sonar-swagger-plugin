package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class ValueTreeImpl extends SWAGGERTree implements ValueTree {

  private final Tree value;

  public ValueTreeImpl(Tree value) {
    this.value = value;
  }

  @Override
  public Kind getKind() {
    return Kind.VALUE;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.singletonIterator(value);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitValue(this);
  }

  @Override
  public Tree value() {
    return value;
  }

}
