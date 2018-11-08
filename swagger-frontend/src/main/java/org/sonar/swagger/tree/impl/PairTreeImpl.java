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

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.*;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class PairTreeImpl extends SWAGGERTree implements PairTree {

  private final KeyTree key;
  private final SyntaxToken colon;
  private final SyntaxToken lineOrSpace;
  private final ValueTree value;

  public PairTreeImpl(KeyTree key, SyntaxToken colon, SyntaxToken lineOrSpace, ValueTree value) {
    this.key = key;
    this.colon = colon;
    this.lineOrSpace = lineOrSpace;
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
  public SyntaxToken lineOrSpace() {
	return lineOrSpace;
  }
}
