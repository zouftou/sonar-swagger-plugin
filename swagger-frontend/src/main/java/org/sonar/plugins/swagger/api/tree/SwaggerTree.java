package org.sonar.plugins.swagger.api.tree;

import java.util.List;

public interface SwaggerTree extends Tree {

  boolean hasByteOrderMark();

  List<ValueTree> values();

}
