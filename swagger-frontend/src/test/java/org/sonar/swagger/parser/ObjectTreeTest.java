package org.sonar.swagger.parser;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.ObjectTree;
import org.sonar.plugins.swagger.api.tree.Tree;

import java.io.File;
import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class ObjectTreeTest extends CommonSwaggerTreeTest {

  public ObjectTreeTest() {
    super(SwaggerLexicalGrammar.OBJECT);
  }

  @Test
  public void object() throws IOException {
    ObjectTree tree;

    tree = checkParsed("  description: \"This is a\"");
    assertThat(tree.entries()).hasSize(1);
    assertThat(tree.entries().get(0).pair().value().is(Tree.Kind.VALUE_SIMPLE));
    
    tree = checkParsed("  description: null");
    assertThat(tree.entries()).hasSize(1);
    assertThat(tree.entries().get(0).pair().value().is(Tree.Kind.NULL));
  }

  @Test
  public void notObject() {
    checkNotParsed("info:");
    checkNotParsed("info:\\n");
    checkNotParsed("info:\n  description:");
  }

  private ObjectTree checkParsed(String toParse) throws IOException {
    ObjectTree tree = (ObjectTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

  private ObjectTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
