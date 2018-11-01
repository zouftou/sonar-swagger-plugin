package org.sonar.swagger.tree.impl;

import java.util.Iterator;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.tree.SingleQuotedStringTree;
import org.sonar.plugins.swagger.api.tree.StringTree;
import org.sonar.plugins.swagger.api.tree.SyntaxToken;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;

import com.google.common.collect.Iterators;

public class SingleQuotedStringTreeImpl  extends SWAGGERTree implements SingleQuotedStringTree {

    private final SyntaxToken leftSingleQuote;
    private final StringTree text;
    private final SyntaxToken rightSingleQuote;
	  
    public SingleQuotedStringTreeImpl(SyntaxToken leftSingleQuote, @Nullable StringTree text, SyntaxToken rightSingleQuote) {
        this.leftSingleQuote = leftSingleQuote;
        this.text = text;
        this.rightSingleQuote = rightSingleQuote;
    }
    
    @Override
    public void accept(DoubleDispatchVisitor visitor) {
      visitor.visitSingleQuotedString(this);
    }

	@Override
	public SyntaxToken leftSingleQuote() {
		return leftSingleQuote;
	}

	@Override
	public StringTree text() {
		return text;
	}

	@Override
	public SyntaxToken rightSingleQuote() {
		return rightSingleQuote;
	}

	@Override
	public Kind getKind() {
		return Kind.DOUBLE_QUOTED_STRING;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
	    return Iterators.forArray(leftSingleQuote,text,rightSingleQuote);
	}

}
