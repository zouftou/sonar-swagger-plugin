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
		ARRAY_INTERNAL(InternalArrayTree.class),
		ARRAY_EMPTY(EmptyArrayTree.class),
		OBJECT(ObjectTree.class),
		OBJECT_ENTRY(ObjectEntryTree.class),
		OBJECT_UNNAMED(UnnamedObjectTree.class),
		PAIR(PairTree.class),
		VALUE(ValueTree.class),
		VALUE_SIMPLE(SimpleValueTree.class),
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
