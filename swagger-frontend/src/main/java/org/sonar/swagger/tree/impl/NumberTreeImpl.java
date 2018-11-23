package org.sonar.swagger.tree.impl;

import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

public class NumberTreeImpl extends LiteralTreeImpl {

	public NumberTreeImpl(SyntaxToken value) {
		super(value);
	}

	@Override
	public Kind getKind() {
		return Kind.NUMBER;
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitNumber(this);
	}

}
