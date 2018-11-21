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

public class EmptyArrayTreeImpl extends SWAGGERTree implements EmptyArrayTree {

  private final SyntaxToken space1;
  private final KeyTree key;
  private final SyntaxToken colon;
  private final SyntaxToken space2;
  private final SyntaxToken emptyArray;

  public EmptyArrayTreeImpl(SyntaxToken space1, KeyTree key, SyntaxToken colon, SyntaxToken space2, SyntaxToken emptyArray) {
	this.space1 = space1;
	this.key = key;
    this.colon = colon;
    this.space2 = space2;
    this.emptyArray = emptyArray;
  }

  @Override
  public Kind getKind() {
    return Kind.EMPTY_ARRAY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(space1, key, colon, space2, emptyArray);
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
    visitor.visitEmptyArray(this);
  }
  
  @Override
  public SyntaxToken space2() {
	return space2;
  }
  
  @Override
  public SyntaxToken space1() {
	return space1;
  }

  @Override
  public SyntaxToken emptyTree() {
	return emptyArray;
  }
}