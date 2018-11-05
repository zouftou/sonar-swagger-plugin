package org.sonar.plugins.swagger.api.tree;

import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitor;
import org.sonar.sslr.grammar.GrammarRuleKey;

public interface Tree {
	
	  boolean is(Kind... kind);

	  void accept(DoubleDispatchVisitor visitor);

	  enum Kind implements GrammarRuleKey {

	    SWAGGER(SwaggerTree.class),
	    ARRAY(ArrayTree.class),
	    ARRAY_ENTRY(ArrayEntryTree.class),
	    OBJECT(ObjectTree.class),
	    PAIR(PairTree.class),
	    VALUE(ValueTree.class),
	    KEY(KeyTree.class),
	    STRING(StringTree.class),
	    NUMBER(LiteralTree.class),
	    FALSE(LiteralTree.class),
	    TRUE(LiteralTree.class),
	    NULL(LiteralTree.class),
	    TOKEN(SyntaxToken.class),
	    SPACING(SyntaxSpacing.class);

	    final Class<? extends Tree> associatedInterface;

	    Kind(Class<? extends Tree> associatedInterface) {
	      this.associatedInterface = associatedInterface;
	    }

	    public Class<? extends Tree> getAssociatedInterface() {
	      return associatedInterface;
	    }
	  }
}
