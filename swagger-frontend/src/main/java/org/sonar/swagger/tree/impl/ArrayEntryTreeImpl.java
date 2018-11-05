package org.sonar.swagger.tree.impl;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.ArrayEntryTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.collect.Iterators;

public class ArrayEntryTreeImpl extends SWAGGERTree implements ArrayEntryTree {

  private final SyntaxToken minus;
  private final SyntaxToken whiteSpace;
  private final ValueTree value;

  public ArrayEntryTreeImpl(SyntaxToken minus, SyntaxToken whiteSpace, ValueTree value) {
    this.minus = minus;
    this.whiteSpace = whiteSpace;
    this.value = value;
  }

  @Override
  public Kind getKind() {
    return Kind.ARRAY_ENTRY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
	
	return Iterators.concat(
	  Iterators.singletonIterator(minus),
	  Iterators.singletonIterator(whiteSpace),
	  Iterators.singletonIterator(value));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitArrayEntry(this);
  }

@Override
public SyntaxToken minus() {
	return minus;
}

@Override
public SyntaxToken whitespace() {
	return whiteSpace;
}

@Override
public ValueTree value() {
	return value;
}

}
