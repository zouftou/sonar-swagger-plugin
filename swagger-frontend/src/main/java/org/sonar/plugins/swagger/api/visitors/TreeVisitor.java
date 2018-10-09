package org.sonar.plugins.swagger.api.visitors;

public interface TreeVisitor {

	  TreeVisitorContext getContext();

	  void scanTree(TreeVisitorContext context);

}
