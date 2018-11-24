package org.sonar.plugins.swagger.api.visitors;

import java.io.File;

import org.junit.Test;
import org.sonar.swagger.tree.impl.InternalSyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.issue.IssueLocation;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class IssueLocationTest {

  private static final File FILE = mock(File.class);
  private static final String MESSAGE = "message";

  @Test
  public void several_lines_tokens() throws Exception {
    String tokenValue = "blabla\\\nblabla...";

    IssueLocation location = new IssueLocation(FILE, createToken(3, 2, tokenValue), MESSAGE);
    assertThat(location.startLine()).isEqualTo(3);
    assertThat(location.endLine()).isEqualTo(4);
    assertThat(location.startLineOffset()).isEqualTo(2);
    assertThat(location.endLineOffset()).isEqualTo(9);
    assertThat(location.file()).isEqualTo(FILE);
  }

  private Tree createToken(int line, int column, String tokenValue) {
    return new InternalSyntaxToken(line, column, tokenValue, false, false);
  }

}
