package org.sonar.swagger.tree.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.ArrayEntryTree;
import org.sonar.plugins.swagger.api.tree.ArrayTree;
import org.sonar.plugins.swagger.api.tree.KeyTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.collect.Iterators;

public class ArrayTreeImpl extends SWAGGERTree implements ArrayTree {

  private final KeyTree key;
  private final SyntaxToken colon;
  private final SyntaxToken newLine;
  private final List<ArrayEntryTree> values;

  public ArrayTreeImpl(KeyTree key, SyntaxToken colon, SyntaxToken newLine, @Nullable List<ArrayEntryTree> values) {
    this.key = key;
    this.colon = colon;
    this.newLine = newLine;
    this.values = values;
  }

  @Override
  public Kind getKind() {
    return Kind.ARRAY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(key),
      Iterators.singletonIterator(colon),values.iterator());
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitArray(this);
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
  public List<ArrayEntryTree> elements() {
    return values != null ? values : Collections.emptyList();
  }

@Override
public SyntaxToken newLine() {
	return newLine;
}

}
