/*
 * SonarQube Swagger Analyzer
 * Copyright (C) 2015-2017 Zouhir OUFTOU
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

import org.sonar.plugins.swagger.api.tree.SyntaxToken;

import static org.fest.assertions.Assertions.assertThat;

public abstract class CommonSyntaxTokenTreeTest extends CommonSwaggerTreeTest {

  private String expectedText;

  public CommonSyntaxTokenTreeTest(SwaggerLexicalGrammar ruleKey) {
    super(ruleKey);
    this.expectedText = null;
  }

  public CommonSyntaxTokenTreeTest(SwaggerLexicalGrammar ruleKey, String expectedText) {
    super(ruleKey);
    this.expectedText = expectedText;
  }

  public void checkParsed(String toParse, String expected) {
    SyntaxToken token = (SyntaxToken) parser().parse(toParse);
    assertThat(token.text()).isEqualTo(expected);
  }

  public void checkParsed(String toParse) {
    checkParsed(
      toParse,
      expectedText != null ? expectedText : toParse);
  }

}
