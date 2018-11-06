package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.*;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class SimplePairTreeImpl extends SWAGGERTree implements SimplePairTree {

  private final KeyTree key;
  private final SyntaxToken colon;
  private final SyntaxToken whiteSpace;
  private final LiteralTree value;

  public SimplePairTreeImpl(KeyTree key, SyntaxToken colon,SyntaxToken whiteSpace, LiteralTree value) {
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
  public LiteralTree value() {
    return value;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitSimplePair(this);
  }

@Override
public SyntaxToken whiteSpace() {
	return whiteSpace;
}

}
