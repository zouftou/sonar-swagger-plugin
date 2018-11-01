package org.sonar.plugins.swagger.api.tree;

public interface SwaggerTree extends Tree {

  boolean hasByteOrderMark();

  ValueTree value();

}
