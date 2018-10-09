package org.sonar.swagger.visitors;

import java.nio.charset.Charset;

@FunctionalInterface
public interface CharsetAwareVisitor {

  void setCharset(Charset charset);

}
