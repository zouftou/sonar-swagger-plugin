package org.sonar.swagger.parser;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.tree.UnnamedObjectTree;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class ObjectUnnamedTreeTest extends CommonSwaggerTreeTest {

  public ObjectUnnamedTreeTest() {
    super(SwaggerLexicalGrammar.OBJECT_UNNAMED);
  }

  @Test
  public void unnamedObject() throws IOException {
	UnnamedObjectTree tree;

    tree = checkParsed("name: \"pet\"\n" + 
    		"  description: \"Everything about your Pets\"\n" + 
    		"  externalDocs:\n" + 
    		"    description: \"Find out more\"");
    assertThat(tree.key().actualText()).isEqualTo("name");
    assertThat(tree.simpleValueTree().is(Tree.Kind.STRING));
    assertThat(tree.objectTree().entries()).hasSize(2);
  }

  @Test
  public void notUnnamedObject() {
    checkNotParsed("name: ");
    checkNotParsed("name:\n");
    checkNotParsed("name: \"pet\"\n  description:");
  }

  private UnnamedObjectTree checkParsed(String toParse) throws IOException {
	UnnamedObjectTree tree = (UnnamedObjectTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

  private UnnamedObjectTree checkParsed(File file) throws IOException {
    return checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
