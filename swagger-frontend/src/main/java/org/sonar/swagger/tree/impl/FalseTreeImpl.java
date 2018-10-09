package org.sonar.swagger.tree.impl;

import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class FalseTreeImpl extends LiteralTreeImpl {

  public FalseTreeImpl(SyntaxToken value) {
    super(value);
  }

  @Override
  public Kind getKind() {
    return Kind.FALSE;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitFalse(this);
  }

}
