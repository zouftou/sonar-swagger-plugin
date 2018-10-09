package org.sonar.swagger.visitors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.sonar.plugins.swagger.api.SwaggerCheck;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.issue.*;

public class Issues {

  private List<Issue> issueList;
  private SwaggerCheck check;

  public Issues(SwaggerCheck check) {
    this.check = check;
    this.issueList = new ArrayList<>();
  }

  public PreciseIssue addPreciseIssue(File file, Tree tree, String message) {
    PreciseIssue issue = new PreciseIssue(check, new IssueLocation(file, tree, message));
    issueList.add(issue);
    return issue;
  }

  public FileIssue addFileIssue(File file, String message) {
    FileIssue issue = new FileIssue(check, file, message);
    issueList.add(issue);
    return issue;
  }

  public LineIssue addLineIssue(File file, int line, String message) {
    LineIssue issue = new LineIssue(check, file, line, message);
    issueList.add(issue);
    return issue;
  }

  public List<Issue> getList() {
    return issueList;
  }

  public void reset() {
    issueList.clear();
  }

}
