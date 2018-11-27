package org.sonar.swagger.parser;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class PairTreeTest extends CommonSwaggerTreeTest {

  public PairTreeTest() {
    super(SwaggerLexicalGrammar.PAIR);
  }

  @Test
  public void pair() {
    PairTree tree;

    tree = checkParsed("key: \"value\"");
    assertThat(tree.key().actualText()).isEqualTo("key");
    assertTrue(tree.value().is(Tree.Kind.VALUE_SIMPLE));

    tree = checkParsed("key:\n  abc: 1\n  def: 2");
    assertThat(tree.key().actualText()).isEqualTo("key");
    assertTrue(tree.value().is(Tree.Kind.OBJECT));
    assertThat(((ObjectTree) tree.value()).entries().size()).isEqualTo(2);

    tree = checkParsed("key: null");
    assertTrue(tree.value().is(Tree.Kind.NULL));

    tree = checkParsed("key: false");
    assertTrue(tree.value().is(Tree.Kind.FALSE));

    tree = checkParsed("key: true");
    assertTrue(tree.value().is(Tree.Kind.TRUE));

    tree = checkParsed("key: 1");
    assertTrue(tree.value().is(Tree.Kind.NUMBER));
  }

  @Test
  public void notPair() {
    checkNotParsed("123");
    checkNotParsed("\"abc\"");
    checkNotParsed("\"abc\":");
    checkNotParsed(":");
    checkNotParsed(": \"ab\"");
  }

  private PairTree checkParsed(String toParse) {
    PairTree tree = (PairTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.key()).isNotNull();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

}
