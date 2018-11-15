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
package org.sonar.swagger.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.swagger.api.tree.*;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class InternalArrayTreeImpl extends SWAGGERTree implements InternalArrayTree {

  private final SyntaxToken space;
  private final KeyTree key;
  private final SyntaxToken colon;
  private final SyntaxToken newLine;
  private final ArrayTree arrayTree;

  public InternalArrayTreeImpl(SyntaxToken space, KeyTree key, SyntaxToken colon, SyntaxToken newLine, ArrayTree arrayTree) {
    this.space = space;
	this.key = key;
    this.colon = colon;
    this.newLine = newLine;
    this.arrayTree = arrayTree;
  }

  @Override
  public Kind getKind() {
    return Kind.INTERNAL_ARRAY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(space, key, colon, newLine, arrayTree);
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
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitInternalArray(this);
  }

  @Override
  public SyntaxToken newLine() {
	return newLine;
  }

  @Override
  public ArrayTree arrayTree() {
	return arrayTree;
  }

  @Override
  public SyntaxToken space() {
	return space;
  }
}
