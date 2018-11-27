package org.sonar.swagger.parser;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.ArrayEntryTree;
import org.sonar.plugins.swagger.api.tree.LiteralTree;
import org.sonar.plugins.swagger.api.tree.SimpleValueTree;
import org.sonar.plugins.swagger.api.tree.Tree;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ArrayEntryTreeTest extends CommonSwaggerTreeTest {

  public ArrayEntryTreeTest() {
    super(SwaggerLexicalGrammar.ARRAY_ENTRY);
  }

  @Test
  public void arrayEntry() throws IOException {
    ArrayEntryTree tree;

    tree = checkParsed("- ftp");
    assertTrue(tree.value().is(Tree.Kind.VALUE_SIMPLE));
    
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.STRING));
    
    tree = checkParsed("- \"http\"");
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.STRING));
    
    tree = checkParsed("- 'https'");
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.STRING));

    tree = checkParsed("- null");
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.NULL));

    tree = checkParsed("- true");
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.TRUE));

    tree = checkParsed("- false");
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.FALSE));

    tree = checkParsed("- 89");
    assertTrue(((SimpleValueTree)tree.value()).value().is(Tree.Kind.NUMBER));
  }

  @Test
  public void notArrayEntry() {
    checkNotParsed("-ftp");
    checkNotParsed("null");
    checkNotParsed("true");
    checkNotParsed("false");
    checkNotParsed("89");
    checkNotParsed("-\"http\"");
    checkNotParsed("-'https'");
  }

  private ArrayEntryTree checkParsed(String toParse) throws IOException {
	ArrayEntryTree tree = (ArrayEntryTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

  private ArrayEntryTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
