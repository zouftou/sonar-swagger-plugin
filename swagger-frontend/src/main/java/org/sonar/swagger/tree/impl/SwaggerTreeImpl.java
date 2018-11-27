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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.base.Functions;
import com.google.common.collect.Iterators;

public class SwaggerTreeImpl extends SWAGGERTree implements SwaggerTree {

	private final SyntaxToken byteOrderMark;
	private final SyntaxToken eof;
	private final SeparatedList<PairTree> pairs;

	public SwaggerTreeImpl(@Nullable SyntaxToken byteOrderMark, @Nullable SeparatedList<PairTree> pairs,
			SyntaxToken eof) {
		this.byteOrderMark = byteOrderMark;
		this.pairs = pairs;
		this.eof = eof;
	}

	@Override
	public Kind getKind() {
		return Kind.SWAGGER;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.concat(Iterators.singletonIterator(byteOrderMark),
				pairs != null ? pairs.elementsAndSeparators(Functions.identity()) : new ArrayList<Tree>().iterator(),
				Iterators.singletonIterator(eof));
	}

	@Override
	public boolean hasByteOrderMark() {
		return byteOrderMark != null;
	}

	@Override
	public List<PairTree> pairs() {
		return pairs;
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitSwagger(this);
	}

}
