package org.sonar.plugins.swagger.api.visitors.issue;

import javax.annotation.Nullable;

import org.sonar.plugins.swagger.api.SwaggerCheck;

public interface Issue {

	SwaggerCheck check();

  @Nullable
  Double cost();

  Issue cost(double cost);

}
