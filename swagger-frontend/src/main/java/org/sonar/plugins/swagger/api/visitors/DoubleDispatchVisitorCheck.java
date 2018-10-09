package org.sonar.plugins.swagger.api.visitors;

import java.util.List;

import org.sonar.swagger.visitors.Issues;
import org.sonar.plugins.swagger.api.SwaggerCheck;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.issue.FileIssue;
import org.sonar.plugins.swagger.api.visitors.issue.Issue;
import org.sonar.plugins.swagger.api.visitors.issue.LineIssue;
import org.sonar.plugins.swagger.api.visitors.issue.PreciseIssue;

public abstract class DoubleDispatchVisitorCheck extends DoubleDispatchVisitor implements SwaggerCheck {

  private Issues issues = new Issues(this);

  @Override
  public List<Issue> scanFile(TreeVisitorContext context) {
    validateParameters();
    issues.reset();
    scanTree(context);
    return issues.getList();
  }

  @Override
  public PreciseIssue addPreciseIssue(Tree tree, String message) {
    return issues.addPreciseIssue(getContext().getFile(), tree, message);
  }

  @Override
  public FileIssue addFileIssue(String message) {
    return issues.addFileIssue(getContext().getFile(), message);
  }

  @Override
  public LineIssue addLineIssue(int line, String message) {
    return issues.addLineIssue(getContext().getFile(), line, message);
  }

  @Override
  public void validateParameters() {
    // Default behavior: do nothing
  }

}
