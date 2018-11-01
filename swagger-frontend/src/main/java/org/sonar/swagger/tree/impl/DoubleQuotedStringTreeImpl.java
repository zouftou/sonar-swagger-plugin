package org.sonar.swagger.tree.impl;

import java.util.Iterator;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.DoubleQuotedStringTree;
import org.sonar.plugins.swagger.api.tree.StringTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.collect.Iterators;

public class DoubleQuotedStringTreeImpl  extends SWAGGERTree implements DoubleQuotedStringTree {

    private final SyntaxToken leftDoubleQuote;
    private final StringTree text;
    private final SyntaxToken rightDoubleQuote;
	  
    public DoubleQuotedStringTreeImpl(SyntaxToken leftDoubleQuote, @Nullable StringTree text, SyntaxToken rightDoubleQuote) {
        this.leftDoubleQuote = leftDoubleQuote;
        this.text = text;
        this.rightDoubleQuote = rightDoubleQuote;
    }
    
    @Override
    public void accept(DoubleDispatchVisitor visitor) {
      visitor.visitDoubleQuotedString(this);
    }

	@Override
	public SyntaxToken leftDoubleQuote() {
		return leftDoubleQuote;
	}

	@Override
	public StringTree text() {
		return text;
	}

	@Override
	public SyntaxToken rightDoubleQuote() {
		return rightDoubleQuote;
	}

	@Override
	public Kind getKind() {
		return Kind.DOUBLE_QUOTED_STRING;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
	    return Iterators.forArray(leftDoubleQuote,text,rightDoubleQuote);
	}

}
