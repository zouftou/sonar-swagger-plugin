/*
 * SonarQube Swagger Analyzer
 * Copyright (C) 2018-2020 Zouhir OUFTOU
 * zouhir.ouftou@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.swagger.api.visitors;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.sonar.swagger.tree.impl.SWAGGERTree;
import org.sonar.plugins.swagger.api.tree.Tree;

public abstract class SubscriptionVisitor implements TreeVisitor {

  private TreeVisitorContext context;
  private Collection<Tree.Kind> nodesToVisit;

  public abstract List<Tree.Kind> nodesToVisit();

  public void visitNode(Tree tree) {
    // Default behavior: do nothing.
  }

  public void leaveNode(Tree tree) {
    // Default behavior: do nothing.
  }

  public void visitFile(Tree tree) {
    // Default behavior: do nothing.
  }

  public void leaveFile(Tree tree) {
    // Default behavior: do nothing.
  }

  @Override
  public TreeVisitorContext getContext() {
    Preconditions.checkState(context != null, "this#scanTree(context) should be called to initialised the context before accessing it");
    return context;
  }

  @Override
  public final void scanTree(TreeVisitorContext context) {
    this.context = context;
    visitFile(context.getTopTree());
    scanTree(context.getTopTree());
    leaveFile(context.getTopTree());
  }

  public void scanTree(Tree tree) {
    nodesToVisit = nodesToVisit();
    visit(tree);
  }

  private void visit(Tree tree) {
    boolean isSubscribed = isSubscribed(tree);
    if (isSubscribed) {
      visitNode(tree);
    }
    visitChildren(tree);
    if (isSubscribed) {
      leaveNode(tree);
    }
  }

  protected boolean isSubscribed(Tree tree) {
    return nodesToVisit.contains(((SWAGGERTree) tree).getKind());
  }

  private void visitChildren(Tree tree) {
	  SWAGGERTree jsonTree = (SWAGGERTree) tree;

    if (!jsonTree.isLeaf()) {
      for (Iterator<Tree> iter = jsonTree.childrenIterator(); iter.hasNext();) {
        Tree next = iter.next();

        if (next != null) {
          visit(next);
        }
      }
    }
  }

}
