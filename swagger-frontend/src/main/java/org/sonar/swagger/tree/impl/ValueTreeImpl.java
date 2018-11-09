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

import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class ValueTreeImpl extends SWAGGERTree implements ValueTree {

  private final SyntaxToken newLine;
  private final Tree value;

  public ValueTreeImpl(SyntaxToken newLine, Tree value) {
	this.newLine = newLine;
    this.value = value;
  }

  @Override
  public Kind getKind() {
    return Kind.VALUE;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.singletonIterator(value);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitValue(this);
  }

  @Override
  public Tree value() {
    return value;
  }

  @Override
  public SyntaxToken newLine() {
	return newLine;
  }
}
