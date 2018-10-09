package org.sonar.swagger.parser;

import com.google.common.annotations.VisibleForTesting;
import com.sonar.sslr.api.typed.ActionParser;

import java.nio.charset.Charset;

import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.sslr.grammar.GrammarRuleKey;

public class SwaggerParserBuilder {

  private SwaggerParserBuilder() {
  }

  public static ActionParser<Tree> createParser(Charset charset) {
    return createParser(charset, SwaggerLexicalGrammar.SWAGGER);
  }

  @VisibleForTesting
  public static ActionParser<Tree> createTestParser(Charset charset, GrammarRuleKey rootRule) {
    return createParser(charset, rootRule);
  }

  private static ActionParser<Tree> createParser(Charset charset, GrammarRuleKey rootRule) {
    return new ActionParser<>(
      charset,
      SwaggerLexicalGrammar.createGrammar(),
      SwaggerGrammar.class,
      new TreeFactory(),
      new SwaggerNodeBuilder(),
      rootRule);
  }

}
