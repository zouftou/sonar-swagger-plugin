//package org.sonar.swagger.parser;
//
//import com.google.common.base.Charsets;
//import com.google.common.io.Files;
//import org.junit.Test;
//import org.sonar.plugins.swagger.api.tree.ArrayTree;
//import org.sonar.plugins.swagger.api.tree.Tree;
//
//import java.io.File;
//import java.io.IOException;
//
//import static org.fest.assertions.Assertions.assertThat;
//import static org.junit.Assert.assertTrue;
//
//public class InternalEntryTreeTest extends CommonSwaggerTreeTest {
//
//  public InternalEntryTreeTest() {
//    super(SwaggerLexicalGrammar.ARRAY);
//  }
//
//  @Test
//  public void array() throws IOException {
//    ArrayTree tree;
//
//    tree = checkParsed("[]");
//    assertThat(tree.elements()).hasSize(0);
//
//    tree = checkParsed(" []");
//    assertThat(tree.elements()).hasSize(0);
//
//    tree = checkParsed("  []");
//    assertThat(tree.elements()).hasSize(0);
//
//    tree = checkParsed("  [ ]");
//    assertThat(tree.elements()).hasSize(0);
//
//    tree = checkParsed("  [  ]");
//    assertThat(tree.elements()).hasSize(0);
//
//    tree = checkParsed("  [ \"abc\" ]");
//    assertThat(tree.elements()).hasSize(1);
//    assertTrue(tree.elements().get(0).value().is(Tree.Kind.STRING));
//
//    tree = checkParsed("  [ \"abc\", \"def\" ]");
//    assertThat(tree.elements()).hasSize(2);
//
//    tree = checkParsed("  [ \"abc\", \"def\" , null ]");
//    assertThat(tree.elements()).hasSize(3);
//    assertTrue(tree.elements().get(2).value().is(Tree.Kind.NULL));
//
//    tree = checkParsed("  [ \"abc\", \"def\", null, true ]");
//    assertThat(tree.elements()).hasSize(4);
//    assertTrue(tree.elements().get(3).value().is(Tree.Kind.TRUE));
//
//    tree = checkParsed("  [ \"abc\", \"def\", null, true , false ]");
//    assertThat(tree.elements()).hasSize(5);
//    assertTrue(tree.elements().get(4).value().is(Tree.Kind.FALSE));
//
//    tree = checkParsed("  [1]");
//    assertThat(tree.elements()).hasSize(1);
//    assertTrue(tree.elements().get(0).value().is(Tree.Kind.NUMBER));
//
//    tree = checkParsed("  [ 1.5 ]");
//    assertThat(tree.elements()).hasSize(1);
//
//    tree = checkParsed("  [ 1.5, 1 ]");
//    assertThat(tree.elements()).hasSize(2);
//
//    tree = checkParsed("  [ 1.5, 1 , 3 ]");
//    assertThat(tree.elements()).hasSize(3);
//
//    tree = checkParsed("  [ 1.5, 1 , \"abc\" ]");
//    assertThat(tree.elements()).hasSize(3);
//
//    tree = checkParsed("  [ 1.5, 1 , \"abc\", null ]");
//    assertThat(tree.elements()).hasSize(4);
//
//    tree = checkParsed("  [ 1.5, 1 , \"abc\", null, true, false ]");
//    assertThat(tree.elements()).hasSize(6);
//
//    tree = checkParsed("  [ 1.5 , {}]");
//    assertThat(tree.elements()).hasSize(2);
//
//    tree = checkParsed("  [ 1.5 , { \"abc\" : 123.4, \"def\": {}}]");
//    assertThat(tree.elements()).hasSize(2);
//
//    tree = checkParsed(new File("src/test/resources/many-values.json"));
//    assertThat(tree.elements()).hasSize(10000);
//  }
//
//  @Test
//  public void notArray() {
//    checkNotParsed("[");
//    checkNotParsed("]");
//    checkNotParsed("\"[]\"");
//  }
//
//  private ArrayTree checkParsed(String toParse) throws IOException {
//    ArrayTree tree = (ArrayTree) parser().parse(toParse);
//    assertThat(tree).isNotNull();
//    //assertThat(tree.leftBracket()).isNotNull();
//    //assertThat(tree.rightBracket()).isNotNull();
//    assertThat(tree.elements()).isNotNull();
//    return tree;
//  }
//
//  private ArrayTree checkParsed(File file) throws IOException {
//    return checkParsed(Files.toString(file, Charsets.UTF_8));
//  }
//
//}
