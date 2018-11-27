/*
 * SonarQube Swagger Analyzer
 * Copyright (C) 2015-2017 Zouhir OUFTOU
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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.ArrayEntryTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.collect.Iterators;

public class ArrayEntryTreeImpl extends SWAGGERTree implements ArrayEntryTree {

	private List<InternalSyntaxToken> indentations;
	private final SyntaxToken minus;
	private final Tree value;

	public ArrayEntryTreeImpl(@Nullable List<InternalSyntaxToken> indentations, SyntaxToken minus, Tree value) {
		this.indentations = indentations != null ? indentations : Collections.emptyList();
		this.minus = minus;
		this.value = value;
	}

	@Override
	public Kind getKind() {
		return Kind.ARRAY_ENTRY;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.concat(indentations.iterator(), Iterators.singletonIterator(minus),
				Iterators.singletonIterator(value));
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
	public Tree value() {
		return value;
	}

	@Override
	public List<InternalSyntaxToken> indentations() {
		return indentations;
	}
}
