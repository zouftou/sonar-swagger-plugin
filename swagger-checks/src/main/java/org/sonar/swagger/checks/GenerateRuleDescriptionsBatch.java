///*
// * SonarQube JSON Analyzer
// * Copyright (C) 2015-2017 David RACODON
// * david.racodon@gmail.com
// *
// * This program is free software; you can redistribute it and/or
// * modify it under the terms of the GNU Lesser General Public
// * License as published by the Free Software Foundation; either
// * version 3 of the License, or (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// * Lesser General Public License for more details.
// *
// * You should have received a copy of the GNU Lesser General Public License
// * along with this program; if not, write to the Free Software Foundation,
// * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
// */
//package org.sonar.swagger.checks;
//
//import java.io.File;
//
//public class GenerateRuleDescriptionsBatch {
//
//  private static final String TEMPLATE_DIRECTORY = "swagger-checks/src/main/resources/org/sonar/l10n/swagger/rules/swagger/template/";
//  private static final String TARGET_DIRECTORY = "swagger-checks/target/classes/org/sonar/l10n/swagger/rules/swagger/";
//
//  private GenerateRuleDescriptionsBatch() {
//  }
//
//  public static void main(String... args) throws Exception {
//    RuleDescriptionsGenerator ruleDescriptionsGenerator = new RuleDescriptionsGenerator();
//    File[] files = new File(TEMPLATE_DIRECTORY).listFiles();
//    for (File file : files) {
//      ruleDescriptionsGenerator.generateHtmlRuleDescription(file.getPath(), TARGET_DIRECTORY + file.getName());
//    }
//  }
//
//}
