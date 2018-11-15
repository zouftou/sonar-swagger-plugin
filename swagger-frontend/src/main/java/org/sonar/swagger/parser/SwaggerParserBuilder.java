/*
 * SonarQube Swagger Analyzer
 * Copyright (C) 2018-2020 Zouhir OUFTOU
 * zouhir.ouftou@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
