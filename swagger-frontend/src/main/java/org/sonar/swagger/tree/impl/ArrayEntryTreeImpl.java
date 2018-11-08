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

import org.sonar.plugins.swagger.api.tree.ArrayEntryTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.collect.Iterators;

public class ArrayEntryTreeImpl extends SWAGGERTree implements ArrayEntryTree {

  private final SyntaxToken minus;
  private final SyntaxToken space;
  private final ValueTree value;

  public ArrayEntryTreeImpl(SyntaxToken minus, SyntaxToken space, ValueTree value) {
    this.minus = minus;
    this.space = space;
    this.value = value;
  }

  @Override
  public Kind getKind() {
    return Kind.ARRAY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
	return Iterators.forArray(minus, space, value);
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
  public SyntaxToken space() {
    return space;
  }

  @Override
  public ValueTree value() {
    return value;
  }

}
