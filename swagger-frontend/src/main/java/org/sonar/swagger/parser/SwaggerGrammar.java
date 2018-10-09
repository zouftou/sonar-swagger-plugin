package org.sonar.swagger.parser;

import org.sonar.swagger.parser.TreeFactory;
import org.sonar.swagger.tree.impl.InternalSyntaxToken;

import com.sonar.sslr.api.typed.GrammarBuilder;

public class SwaggerGrammar {

	  private final GrammarBuilder<InternalSyntaxToken> b;
	  private final TreeFactory f;

	  public SwaggerGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
	    this.b = b;
	    this.f = f;
	  }
	  
}
