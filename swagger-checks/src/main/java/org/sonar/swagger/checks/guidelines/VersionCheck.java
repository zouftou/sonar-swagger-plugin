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
package org.sonar.swagger.checks.guidelines;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.swagger.api.SwaggerKeyword;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SimpleValueTree;
import org.sonar.plugins.swagger.api.tree.StringTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.swagger.checks.CheckUtils;
import org.sonar.swagger.checks.Tags;

import com.google.common.annotations.VisibleForTesting;

@Rule(
  key = "swagger-version",
  name = "Swagger version should be x.y.z",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION})
@ActivatedByDefault
@SqaleConstantRemediation("2min")
public class VersionCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT_REGULAR_EXPRESSION = "^(\\d+\\.)?(\\d+\\.)?(\\*|\\d+)$";
  private static final String DEFAULT_MESSAGE = "The regular expression matches Swagger version.";
  
  private String regularExpression = DEFAULT_REGULAR_EXPRESSION;

  private String message = DEFAULT_MESSAGE;

  @Override
  public void visitPair(PairTree tree) {
	  String swaggerKey = tree.key().actualText();
	  if((SwaggerKeyword.SWAGGER).equals(swaggerKey)) {
		  SimpleValueTree simpleValue = (SimpleValueTree) tree.value();
		  StringTree stringValue = (StringTree) simpleValue.value();
	      if (Pattern.compile(regularExpression).matcher(stringValue.actualText()).matches()) {
	        addPreciseIssue(tree, message);
	      }
	  }
    super.visitPair(tree);
  }

  @Override
  public void validateParameters() {
    try {
      Pattern.compile(regularExpression);
    } catch (PatternSyntaxException exception) {
      throw new IllegalStateException(
        CheckUtils.paramsErrorMessage(
          this.getClass(),
          "regularExpression parameter \"" + regularExpression + "\" is not a valid regular expression."),
        exception);
    }
  }

  @VisibleForTesting
  public void setRegularExpression(String regularExpression) {
    this.regularExpression = regularExpression;
  }

  @VisibleForTesting
  public void setMessage(String message) {
    this.message = message;
  }

}
