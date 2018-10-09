package org.sonar.swagger.checks.generic;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.swagger.checks.Tags;
//import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitorCheck;
//import org.sonar.squidbridge.annotations.ActivatedByDefault;
//import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "S2260",
  name = "JSON parser failure",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
//@ActivatedByDefault
//@SqaleConstantRemediation("5min")
public class ParsingErrorCheck {//extends DoubleDispatchVisitorCheck {
	
}
