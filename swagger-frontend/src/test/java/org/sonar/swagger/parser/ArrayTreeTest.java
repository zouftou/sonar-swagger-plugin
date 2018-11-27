package org.sonar.swagger.parser;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.ArrayTree;
import org.sonar.plugins.swagger.api.tree.PairTree;
import org.sonar.plugins.swagger.api.tree.SimpleValueTree;
import org.sonar.plugins.swagger.api.tree.Tree;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ArrayTreeTest extends CommonSwaggerTreeTest {

  public ArrayTreeTest() {
    super(SwaggerLexicalGrammar.ARRAY);
  }

  @Test
  public void array() throws IOException {
    ArrayTree tree;

    tree = checkParsed("- \"rrr\"\n- \"sss\"");
    assertThat(tree.entries()).hasSize(2);
    
    tree = checkParsed("- ftp\r\n- \"https\"\r\n- 'http'");
    assertThat(tree.entries()).hasSize(3);
    
    tree = checkParsed("- ftp\n- \"https\"\n- 'http'");
    assertTrue(tree.entries().get(0).value().is(Tree.Kind.VALUE_SIMPLE));

    tree = checkParsed("- null");
    assertTrue(((SimpleValueTree)tree.entries().get(0).value()).value().is(Tree.Kind.NULL));

    tree = checkParsed("- null\n- true");
    assertTrue(((SimpleValueTree)tree.entries().get(1).value()).value().is(Tree.Kind.TRUE));

    tree = checkParsed("- null\n- true\n- false");
    assertTrue(((SimpleValueTree)tree.entries().get(2).value()).value().is(Tree.Kind.FALSE));

    tree = checkParsed("- null\n- true\n- false\n- 89");
    assertTrue(((SimpleValueTree)tree.entries().get(3).value()).value().is(Tree.Kind.NUMBER));
  }

  @Test
  public void notArray() {
    checkNotParsed("schemes:");
    checkNotParsed("schemes:\n");
    checkNotParsed("schemes:\n-https");
  }

  private ArrayTree checkParsed(String toParse) throws IOException {
	ArrayTree tree = (ArrayTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

  private ArrayTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
