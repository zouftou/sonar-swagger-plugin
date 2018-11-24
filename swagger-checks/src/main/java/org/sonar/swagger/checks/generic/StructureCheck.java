package org.sonar.swagger.checks.generic;

import java.util.Iterator;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.swagger.api.tree.ArrayEntryTree;
import org.sonar.plugins.swagger.api.tree.ArrayTree;
import org.sonar.plugins.swagger.api.tree.ObjectEntryTree;
import org.sonar.plugins.swagger.api.tree.ObjectTree;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.swagger.checks.Tags;
import org.sonar.swagger.tree.impl.SWAGGERTree;

@Rule(
		key = "structure",
		name = "Swagger structure is not valide",
		priority = Priority.MAJOR,
		tags = {Tags.CONVENTION })
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class StructureCheck extends DoubleDispatchVisitorCheck {

	private static final String DEFAULT_MESSAGE = "The Swagger structure is not valid";

	@RuleProperty(
			key = "message",
			description = "The issue message",
			defaultValue = DEFAULT_MESSAGE)
	private String message = DEFAULT_MESSAGE;
	
	@Override
	public void visitSwagger(SwaggerTree tree) {
		List<PairTree> pairs = tree.pairs();
		
		for(PairTree pair : pairs) {
			if(pair.value() instanceof ValueTree) {
				checkChildren(((ValueTree)pair.value()).value());
			}
		}
		super.visitSwagger(tree);
	}
	
	public void checkChildren(Tree tree) {
		Iterator<Tree> childrenIterator = ((SWAGGERTree) tree).childrenIterator();

		Tree child;
/*		
		if(tree instanceof ObjectTree) {
			while (childrenIterator.hasNext()) {
				child = childrenIterator.next();
				if(child instanceof ObjectEntryTree) {
					if (!((ObjectEntryTree)child).indentations().isEmpty()) {
						addPreciseIssue(child, message);
					}
				}
			}
		}else if(tree instanceof ArrayTree) {
			while (childrenIterator.hasNext()) {
				child = childrenIterator.next();
				if(child instanceof ArrayEntryTree) {
					if (((ArrayEntryTree)child).indentations().isEmpty()) {
						addPreciseIssue(child, message);
					}
				}
			}
		}*/
	}
}
