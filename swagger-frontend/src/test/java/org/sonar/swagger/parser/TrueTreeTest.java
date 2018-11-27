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

import org.junit.Test;

public class TrueTreeTest extends CommonSyntaxTokenTreeTest {

  public TrueTreeTest() {
    super(SwaggerLexicalGrammar.TRUE, "true");
  }

  @Test
  public void isTrue() {
    checkParsed("true");
    checkParsed(" true", "true");
    checkParsed("  true", "true");
  }

  @Test
  public void notTrue() {
    checkNotParsed("TRUE");
  }

}
