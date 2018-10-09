package org.sonar.swagger.tree.impl;

import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class TrueTreeImpl extends LiteralTreeImpl {

  public TrueTreeImpl(SyntaxToken value) {
    super(value);
  }

  @Override
  public Kind getKind() {
    return Kind.TRUE;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitTrue(this);
  }

}
