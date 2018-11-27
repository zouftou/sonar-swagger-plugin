package org.sonar.swagger.parser;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.InternalArrayTree;
import org.sonar.plugins.swagger.api.tree.Tree;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ArrayInternalTreeTest extends CommonSwaggerTreeTest {

  public ArrayInternalTreeTest() {
    super(SwaggerLexicalGrammar.ARRAY_INTERNAL);
  }

  @Test
  public void internalArray() throws IOException {
    InternalArrayTree tree;

    tree = checkParsed(" tab:\n- ftp\n- \"http\"");
    assertTrue(tree.arrayTree().is(Tree.Kind.ARRAY));
    assertThat(tree.arrayTree().entries().size()).isEqualTo(2);

  }

  @Test
  public void notArray() {
    checkNotParsed("tab:\n- ftp\n- \"http\"");
    checkNotParsed("- ftp\n- \"http\"");
  }

  private InternalArrayTree checkParsed(String toParse) throws IOException {
	  InternalArrayTree tree = (InternalArrayTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.arrayTree()).isNotNull();
    return tree;
  }

  private InternalArrayTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
