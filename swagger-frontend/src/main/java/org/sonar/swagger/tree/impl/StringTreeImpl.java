package org.sonar.swagger.tree.impl;

import org.sonar.plugins.swagger.api.tree.StringTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class StringTreeImpl extends LiteralTreeImpl implements StringTree {

  public StringTreeImpl(SyntaxToken value) {
    super(value);
  }

  @Override
  public Kind getKind() {
    return Kind.STRING;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitString(this);
  }

  @Override
  public String actualText() {
    return text().substring(1, text().length() - 1);
  }
}
