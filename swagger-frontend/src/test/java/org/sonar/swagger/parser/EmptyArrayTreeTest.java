package org.sonar.swagger.parser;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.EmptyArrayTree;
import org.sonar.plugins.swagger.api.tree.Tree;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class EmptyArrayTreeTest extends CommonSwaggerTreeTest {

  public EmptyArrayTreeTest() {
    super(SwaggerLexicalGrammar.ARRAY_EMPTY);
  }

  @Test
  public void emptyArray() throws IOException {
    EmptyArrayTree tree;

    tree = checkParsed(" tab: []");
    assertThat(tree.emptyTree().is(Tree.Kind.TOKEN));
  }

  @Test
  public void notArray() {
    checkNotParsed(" tab: [");
    checkNotParsed(" tab: ]");
    checkNotParsed("\"[ tab: ]\"");
  }

  private EmptyArrayTree checkParsed(String toParse) throws IOException {
	EmptyArrayTree tree = (EmptyArrayTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.emptyTree()).isNotNull();
    return tree;
  }

  private EmptyArrayTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
