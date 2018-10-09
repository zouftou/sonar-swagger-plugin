package org.sonar.plugins.swagger.api.tree;

import org.sonar.sslr.grammar.GrammarRuleKey;

public interface Tree {

  boolean is(Kind... kind);

  //void accept(DoubleDispatchVisitor visitor);

  enum Kind implements GrammarRuleKey {

    SWAGGER(SwaggerTree.class);

    final Class<? extends Tree> associatedInterface;

    Kind(Class<? extends Tree> associatedInterface) {
      this.associatedInterface = associatedInterface;
    }

    public Class<? extends Tree> getAssociatedInterface() {
      return associatedInterface;
    }
  }

}
