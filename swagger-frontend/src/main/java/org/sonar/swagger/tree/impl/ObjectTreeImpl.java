package org.sonar.swagger.tree.impl;

import com.google.common.base.Functions;
import com.google.common.collect.Iterators;

import org.sonar.plugins.swagger.api.tree.KeyTree;
import org.sonar.plugins.swagger.api.tree.ObjectTree;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ObjectTreeImpl extends SWAGGERTree implements ObjectTree {

  private final KeyTree key;
  private final SyntaxToken colon;
  private final SeparatedList<PairTree> pairs;

  public ObjectTreeImpl(KeyTree key, SyntaxToken colon, @Nullable SeparatedList<PairTree> pairs) {
    this.key = key;
	this.colon = colon;
    this.pairs = pairs;
  }

  @Override
  public Kind getKind() {
    return Kind.OBJECT;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(key),
      Iterators.singletonIterator(colon),
      pairs != null ? pairs.elementsAndSeparators(Functions.identity()) : new ArrayList<Tree>().iterator());
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitObject(this);
  }

  @Override
  public SyntaxToken colon() {
    return colon;
  }

  @Override
  public List<PairTree> pairs() {
    return pairs != null ? pairs : Collections.emptyList();
  }

  @Override
  public KeyTree key() {
	return key;
  }
}
