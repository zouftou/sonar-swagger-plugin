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
