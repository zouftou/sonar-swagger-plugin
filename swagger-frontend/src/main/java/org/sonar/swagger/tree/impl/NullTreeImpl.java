package org.sonar.swagger.tree.impl;

import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class NullTreeImpl extends LiteralTreeImpl {

  public NullTreeImpl(SyntaxToken value) {
    super(value);
  }

  @Override
  public Kind getKind() {
    return Kind.NULL;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitNull(this);
  }

}
