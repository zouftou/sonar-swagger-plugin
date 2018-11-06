package org.sonar.swagger.tree.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.KeyTree;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SimplePairTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.UnnamedObjectTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.base.Functions;
import com.google.common.collect.Iterators;

public class UnnamedObjectTreeImpl extends SWAGGERTree implements UnnamedObjectTree {

  private final SimplePairTree simplePaire;
  private final SyntaxToken newLine;
  private final SeparatedList<PairTree> pairs;

  public UnnamedObjectTreeImpl(SimplePairTree simplePaire, SyntaxToken newLine,  @Nullable SeparatedList<PairTree> pairs) {
	this.simplePaire = simplePaire;
	this.newLine = newLine;
    this.pairs = pairs;
  }

  @Override
  public Kind getKind() {
    return Kind.OBJECT;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(simplePaire),
      pairs != null ? pairs.elementsAndSeparators(Functions.identity()) : new ArrayList<Tree>().iterator());
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitUnnamedObject(this);
  }

  @Override
  public List<PairTree> pairs() {
    return pairs != null ? pairs : Collections.emptyList();
  }

  @Override
  public SimplePairTree simplePaire() {
	return simplePaire;
  }

  @Override
  public SyntaxToken newLine() {
	return newLine;
  }
}
