package org.sonar.swagger.parser;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.ObjectEntryTree;
import org.sonar.plugins.swagger.api.tree.Tree;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ObjectEntryTreeTest extends CommonSwaggerTreeTest {

  public ObjectEntryTreeTest() {
    super(SwaggerLexicalGrammar.OBJECT_ENTRY);
  }

  @Test
  public void object() throws IOException {
    ObjectEntryTree tree;

    tree = checkParsed("  title: \"abc\"");
    assertThat(tree.pair().value().is(Tree.Kind.VALUE_SIMPLE));
    
    tree = checkParsed("  title: 'abc'");
    assertThat(tree.pair().value().is(Tree.Kind.VALUE_SIMPLE));
    
    tree = checkParsed("    title: abc");
    assertThat(tree.pair().value().is(Tree.Kind.VALUE_SIMPLE));
  }

  @Test
  public void notObject() {
    checkNotParsed("title: \"abc\"");
    checkNotParsed(" title: \'abc\'");
    checkNotParsed("\"abc\"");
  }

  private ObjectEntryTree checkParsed(String toParse) throws IOException {
    ObjectEntryTree tree = (ObjectEntryTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

  private ObjectEntryTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
