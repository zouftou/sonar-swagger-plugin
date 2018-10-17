package org.sonar.plugins.swagger.api.visitors;

import com.google.common.base.Preconditions;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.swagger.tree.impl.SWAGGERTree;
import org.sonar.plugins.swagger.api.tree.*;

public abstract class DoubleDispatchVisitor implements TreeVisitor {

  private TreeVisitorContext context = null;

  @Override
  public TreeVisitorContext getContext() {
    Preconditions.checkState(context != null, "this#scanTree(context) should be called to initialised the context before accessing it");
    return context;
  }

  @Override
  public final void scanTree(TreeVisitorContext context) {
    this.context = context;
    scan(context.getTopTree());
  }

  protected void scan(@Nullable Tree tree) {
    if (tree != null) {
      tree.accept(this);
    }
  }

  protected void scanChildren(Tree tree) {
    Iterator<Tree> childrenIterator = ((SWAGGERTree) tree).childrenIterator();

    Tree child;

    while (childrenIterator.hasNext()) {
      child = childrenIterator.next();
      if (child != null) {
        child.accept(this);
      }
    }
  }

  protected <T extends Tree> void scan(List<T> trees) {
    trees.forEach(this::scan);
  }

  public void visitJson(SWAGGERTree tree) {
    scanChildren(tree);
  }

  public void visitObject(ObjectTree tree) {
    scanChildren(tree);
  }

  public void visitArray(ArrayTree tree) {
    scanChildren(tree);
  }

  public void visitPair(PairTree tree) {
    scanChildren(tree);
  }

  public void visitKey(KeyTree tree) {
    scanChildren(tree);
  }

  public void visitValue(ValueTree tree) {
    scanChildren(tree);
  }

  public void visitString(StringTree tree) {
    scanChildren(tree);
  }

  public void visitNumber(LiteralTree tree) {
    scanChildren(tree);
  }

  public void visitFalse(LiteralTree tree) {
    scanChildren(tree);
  }

  public void visitTrue(LiteralTree tree) {
    scanChildren(tree);
  }

  public void visitNull(LiteralTree tree) {
    scanChildren(tree);
  }

  public void visitToken(SyntaxToken token) {
  }

}