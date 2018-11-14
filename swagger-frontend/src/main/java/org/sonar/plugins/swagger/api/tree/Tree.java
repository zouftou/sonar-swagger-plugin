/*
 * SonarQube JSON Analyzer
 * Copyright (C) 2015-2017 David RACODON
 * david.racodon@gmail.com
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
    INTERNAL_ARRAY(InternalArrayTree.class),
    EMPTY_ARRAY(EmptyArrayTree.class),
    OBJECT(ObjectTree.class),
    OBJECT_ENTRY(ObjectEntryTree.class),
    UNNAMED_OBJECT(UnnamedObjectTree.class),
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
