package org.sonar.swagger.parser;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.ValueTree;

import static org.fest.assertions.Assertions.assertThat;

public class SimpleValueTreeTest extends CommonSwaggerTreeTest {

  public SimpleValueTreeTest() {
    super(SwaggerLexicalGrammar.VALUE);
  }

  @Test
  public void value() {
    checkParsed("[]", Tree.Kind.ARRAY);
    checkParsed(" []", Tree.Kind.ARRAY);
    checkParsed(" [ ]", Tree.Kind.ARRAY);
    checkParsed("{}", Tree.Kind.OBJECT);
    checkParsed(" {}", Tree.Kind.OBJECT);
    checkParsed(" { }", Tree.Kind.OBJECT);
    checkParsed("null", Tree.Kind.NULL);
    checkParsed(" null", Tree.Kind.NULL);
    checkParsed("true", Tree.Kind.TRUE);
    checkParsed(" true", Tree.Kind.TRUE);
    checkParsed("false", Tree.Kind.FALSE);
    checkParsed("\"abc\"", Tree.Kind.STRING);
    checkParsed(" \"abc\"", Tree.Kind.STRING);
    checkParsed("1", Tree.Kind.NUMBER);
    checkParsed("1.5", Tree.Kind.NUMBER);
    checkParsed(" 1", Tree.Kind.NUMBER);
    checkParsed(" 1.5", Tree.Kind.NUMBER);
    checkParsed("-1.5", Tree.Kind.NUMBER);
    checkParsed(" -1.5", Tree.Kind.NUMBER);
  }

  @Test
  public void notValue() {
    checkNotParsed("abc");
  }

  private void checkParsed(String toParse, Tree.Kind kind) {
    ValueTree tree = (ValueTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.value()).isNotNull();
    assertThat(tree.value().is(kind)).isTrue();
  }

}
