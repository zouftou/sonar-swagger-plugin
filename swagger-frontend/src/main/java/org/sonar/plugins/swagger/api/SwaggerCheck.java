package org.sonar.plugins.swagger.api;

import java.util.List;

import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.TreeVisitorContext;
import org.sonar.plugins.swagger.api.visitors.issue.FileIssue;
import org.sonar.plugins.swagger.api.visitors.issue.Issue;
import org.sonar.plugins.swagger.api.visitors.issue.LineIssue;
import org.sonar.plugins.swagger.api.visitors.issue.PreciseIssue;

public interface SwaggerCheck {

	PreciseIssue addPreciseIssue(Tree tree, String message);

	FileIssue addFileIssue(String message);

	LineIssue addLineIssue(int line, String message);

	List<Issue> scanFile(TreeVisitorContext context);

	void validateParameters();

}
