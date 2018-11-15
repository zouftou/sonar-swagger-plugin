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
package org.sonar.plugins.swagger;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Rule;
import org.sonar.swagger.checks.CheckList;
import org.sonar.swagger.checks.generic.FileNameCheck;

import static org.fest.assertions.Assertions.assertThat;

public class SwaggerRulesDefinitionTest {

  @Test
  public void test() {
    SwaggerRulesDefinition rulesDefinition = new SwaggerRulesDefinition();
    RulesDefinition.Context context = new RulesDefinition.Context();
    rulesDefinition.define(context);
    RulesDefinition.Repository repository = context.repository("swagger");

    assertThat(repository.name()).isEqualTo("SonarQube");
    assertThat(repository.language()).isEqualTo("swagger");
    assertThat(repository.rules()).hasSize(CheckList.getChecks().size());

    RulesDefinition.Rule fileNameRule = repository.rule(FileNameCheck.class.getAnnotation(Rule.class).key());
    assertThat(fileNameRule).isNotNull();
    assertThat(fileNameRule.name()).isEqualTo(FileNameCheck.class.getAnnotation(Rule.class).name());
  }

}
