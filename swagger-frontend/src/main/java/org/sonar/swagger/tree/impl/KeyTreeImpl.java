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

import org.sonar.plugins.swagger.api.tree.KeyTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class KeyTreeImpl extends SWAGGERTree implements KeyTree {

  private final SyntaxToken key;

  public KeyTreeImpl(SyntaxToken key) {
    this.key = key;
  }

  @Override
  public Tree.Kind getKind() {
    return Tree.Kind.KEY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.singletonIterator(key);
  }

  @Override
  public String actualText() {
    return key.text().substring(1, key.text().length() - 1);
  }

  @Override
  public SyntaxToken value() {
    return key;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitKey(this);
  }

}
