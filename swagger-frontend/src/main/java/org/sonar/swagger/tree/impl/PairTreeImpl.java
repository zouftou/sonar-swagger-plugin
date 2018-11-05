package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.*;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class PairTreeImpl extends SWAGGERTree implements PairTree {

  private final SyntaxToken indentation;
  private final KeyTree key;
  private final SyntaxToken colon;
  private final SyntaxToken whiteSpace;
  private final ValueTree value;

  public PairTreeImpl(SyntaxToken indentation, KeyTree key, SyntaxToken colon,SyntaxToken whiteSpace, ValueTree value) {
	this.indentation = indentation;
    this.key = key;
    this.colon = colon;
    this.whiteSpace = whiteSpace;
    this.value = value;
  }

  @Override
  public Kind getKind() {
    return Kind.PAIR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(key, colon, value);
  }

  @Override
  public KeyTree key() {
    return key;
  }

  @Override
  public SyntaxToken colon() {
    return colon;
  }

  @Override
  public ValueTree value() {
    return value;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitPair(this);
  }

@Override
public SyntaxToken indentation() {
	return indentation;
}

@Override
public SyntaxToken whiteSpace() {
	return whiteSpace;
}

}
