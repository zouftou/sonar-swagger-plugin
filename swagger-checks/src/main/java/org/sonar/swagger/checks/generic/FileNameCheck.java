package org.sonar.swagger.checks.generic;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.swagger.checks.CheckUtils;
import org.sonar.swagger.checks.Tags;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.SubscriptionVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(key = "S1578", name = "File names should comply with a naming convention", priority = Priority.MINOR, tags = {
		Tags.CONVENTION })
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class FileNameCheck extends SubscriptionVisitorCheck {

	public static final String DEFAULT = "^[A-Za-z][-_A-Za-z0-9]*\\.yaml$";

	@RuleProperty(key = "format", defaultValue = DEFAULT, description = "Regular expression that file names should match. See "
			+ CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC + " for detailed regular expression syntax.")
	private String format = DEFAULT;

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.of(Tree.Kind.SWAGGER);
	}

	@Override
	public void visitFile(Tree tree) {
		if (!Pattern.compile(format).matcher(getContext().getFile().getName()).matches()) {
			addFileIssue("Rename this file to match this regular expression: " + format);
		}
	}

	@Override
	public void validateParameters() {
		try {
			Pattern.compile(format);
		} catch (PatternSyntaxException exception) {
			throw new IllegalStateException(CheckUtils.paramsErrorMessage(this.getClass(),
					"format parameter \"" + format + "\" is not a valid regular expression."), exception);
		}
	}

	@VisibleForTesting
	public void setFormat(String format) {
		this.format = format;
	}
	
}
