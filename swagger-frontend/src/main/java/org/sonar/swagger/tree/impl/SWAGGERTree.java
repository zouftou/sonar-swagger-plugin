package org.sonar.swagger.tree.impl;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;

public abstract class SWAGGERTree implements Tree {

  public int getLine() {
    return getFirstToken().line();
  }

  @Override
  public final boolean is(Tree.Kind... kind) {
    if (getKind() != null) {
      for (Kind kindIter : kind) {
        if (getKind() == kindIter) {
          return true;
        }
      }
    }
    return false;
  }

  public abstract Kind getKind();

  /**
   * Creates iterator for children of this node.
   * Note that iterator may contain {@code null} elements.
   *
   * @throws UnsupportedOperationException if {@link #isLeaf()} returns {@code true}
   */
  public abstract Iterator<Tree> childrenIterator();

  public boolean isLeaf() {
    return false;
  }

  public SyntaxToken getLastToken() {
    SyntaxToken lastToken = null;
    Iterator<Tree> childrenIterator = childrenIterator();
    while (childrenIterator.hasNext()) {
      SWAGGERTree child = (SWAGGERTree) childrenIterator.next();
      if (child != null) {
        SyntaxToken childLastToken = child.getLastToken();
        if (childLastToken != null) {
          lastToken = childLastToken;
        }
      }
    }
    return lastToken;
  }

  public SyntaxToken getFirstToken() {
    Iterator<Tree> childrenIterator = childrenIterator();
    Tree child;
    do {
      if (childrenIterator.hasNext()) {
        child = childrenIterator.next();
      } else {
        throw new IllegalStateException("Tree has no non-null children " + getKind());
      }
    } while (child == null);
    return ((SWAGGERTree) child).getFirstToken();
  }
}
