package org.sonar.swagger.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.swagger.api.SwaggerCheck;

public class CheckUtils {

  public static final String LINK_TO_JAVA_REGEX_PATTERN_DOC = "http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html";

  private CheckUtils() {
  }

  public static String paramsErrorMessage(Class<? extends SwaggerCheck> clazz, String message) {
    return "Check swagger:" + clazz.getAnnotation(Rule.class).key()
      + " (" + clazz.getAnnotation(Rule.class).name() + "): "
      + message;
  }

}
