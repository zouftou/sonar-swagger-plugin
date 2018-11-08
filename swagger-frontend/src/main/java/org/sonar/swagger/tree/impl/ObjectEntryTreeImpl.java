/*
 * SonarQube JSON Analyzer
 * Copyright (C) 2015-2017 David RACODON
 * david.racodon@gmail.com
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
package org.sonar.swagger.tree.impl;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.ObjectEntryTree;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.collect.Iterators;

public class ObjectEntryTreeImpl extends SWAGGERTree implements ObjectEntryTree {

  private final SyntaxToken indentation;
  private final PairTree pair;

  public ObjectEntryTreeImpl(SyntaxToken indentation, PairTree pair) {
    this.indentation = indentation;
    this.pair = pair;
  }

  @Override
  public Kind getKind() {
    return Kind.OBJECT_ENTRY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
	return Iterators.forArray(indentation,pair);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitObjectEntry(this);
  }

  @Override
  public SyntaxToken indentation() {
	return indentation;
  }

  @Override
  public PairTree pair() {
	return pair;
  }
}
