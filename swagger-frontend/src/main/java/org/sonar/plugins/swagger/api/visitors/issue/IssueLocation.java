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
package org.sonar.plugins.swagger.api.visitors.issue;

import java.io.File;
import javax.annotation.Nullable;

import org.sonar.swagger.tree.impl.SWAGGERTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;

public class IssueLocation {

	private final File file;
	private final SyntaxToken firstToken;
	private final SyntaxToken lastToken;
	private final String message;

	public IssueLocation(File file, Tree tree, @Nullable String message) {
		this(file, tree, tree, message);
	}

	public IssueLocation(File file, Tree firstTree, Tree lastTree, @Nullable String message) {
		this.file = file;
		this.firstToken = ((SWAGGERTree) firstTree).getFirstToken();
		this.lastToken = ((SWAGGERTree) lastTree).getLastToken();
		this.message = message;
	}

	public File file() {
		return file;
	}

	@Nullable
	public String message() {
		return message;
	}

	public int startLine() {
		return firstToken.line();
	}

	public int startLineOffset() {
		return firstToken.column();
	}

	public int endLine() {
		return lastToken.endLine();
	}

	public int endLineOffset() {
		return lastToken.endColumn();
	}

}
