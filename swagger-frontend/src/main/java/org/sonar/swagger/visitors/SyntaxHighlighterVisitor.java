package org.sonar.swagger.visitors;

import com.google.common.collect.ImmutableList;

import java.util.List;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.highlighting.NewHighlighting;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.plugins.swagger.api.tree.KeyTree;
import org.sonar.plugins.swagger.api.tree.LiteralTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.SubscriptionVisitorCheck;

public class SyntaxHighlighterVisitor extends SubscriptionVisitorCheck {

	private final SensorContext sensorContext;
	private final FileSystem fileSystem;
	private NewHighlighting highlighting;

	public SyntaxHighlighterVisitor(SensorContext sensorContext) {
		this.sensorContext = sensorContext;
		fileSystem = sensorContext.fileSystem();
	}

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.<Tree.Kind>builder().add(Tree.Kind.KEY).add(Tree.Kind.STRING).add(Tree.Kind.NUMBER)
				.add(Tree.Kind.TRUE).add(Tree.Kind.FALSE).add(Tree.Kind.NULL).build();
	}

	@Override
	public void visitFile(Tree tree) {
		highlighting = sensorContext.newHighlighting()
				.onFile(fileSystem.inputFile(fileSystem.predicates().is(getContext().getFile())));
	}

	@Override
	public void leaveFile(Tree scriptTree) {
		highlighting.save();
	}

	@Override
	public void visitNode(Tree tree) {
		SyntaxToken token = null;
		TypeOfText code = null;

		if (tree.is(Tree.Kind.KEY)) {
			token = ((KeyTree) tree).value();
			code = TypeOfText.KEYWORD;

		} else if (tree.is(Tree.Kind.STRING)) {
			token = ((LiteralTree) tree).value();
			code = TypeOfText.STRING;

		} else if (tree.is(Tree.Kind.FALSE, Tree.Kind.TRUE, Tree.Kind.NULL, Tree.Kind.NUMBER)) {
			token = ((LiteralTree) tree).value();
			code = TypeOfText.CONSTANT;
		}

		if (token != null) {
			highlight(token, code);
		}
	}

	private void highlight(SyntaxToken token, TypeOfText type) {
		highlighting.highlight(token.line(), token.column(), token.endLine(), token.endColumn(), type);
	}

}
