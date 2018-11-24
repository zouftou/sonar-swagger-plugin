//package org.sonar.swagger.parser;
//
//import com.google.common.base.Charsets;
//import com.google.common.io.Files;
//import org.junit.Test;
//import org.sonar.plugins.swagger.api.tree.ObjectTree;
//import org.sonar.plugins.swagger.api.tree.Tree;
//
//import java.io.File;
//import java.io.IOException;
//
//import static org.fest.assertions.Assertions.assertThat;
//import static org.junit.Assert.assertTrue;
//
//public class ObjectEntryTreeTest extends CommonSwaggerTreeTest {
//
//  public ObjectEntryTreeTest() {
//    super(SwaggerLexicalGrammar.OBJECT);
//  }
//
//  @Test
//  public void object() throws IOException {
//    ObjectTree tree;
//
//    tree = checkParsed("{}");
//    assertThat(tree.pairs()).hasSize(0);
//
//    tree = checkParsed(" {}");
//    assertThat(tree.pairs()).hasSize(0);
//
//    tree = checkParsed("  {}");
//    assertThat(tree.pairs()).hasSize(0);
//
//    tree = checkParsed("  { }");
//    assertThat(tree.pairs()).hasSize(0);
//
//    tree = checkParsed("  {  }");
//    assertThat(tree.pairs()).hasSize(0);
//
//    tree = checkParsed("  { \"abc\": 1 }");
//    assertThat(tree.pairs()).hasSize(1);
//    assertTrue(tree.pairs().get(0).is(Tree.Kind.PAIR));
//    assertThat(tree.pairs().get(0).key().actualText()).isEqualTo("abc");
//    assertTrue(tree.pairs().get(0).value().value().is(Tree.Kind.NUMBER));
//
//    tree = checkParsed("  { \"abc\": 1, \"def\": null }");
//    assertThat(tree.pairs()).hasSize(2);
//    assertTrue(tree.pairs().get(0).is(Tree.Kind.PAIR));
//    assertTrue(tree.pairs().get(1).is(Tree.Kind.PAIR));
//    assertTrue(tree.pairs().get(1).value().value().is(Tree.Kind.NULL));
//
//    tree = checkParsed(" { \"abc\": \"def\", \"zzz\" : [] }");
//    assertThat(tree.pairs()).hasSize(2);
//    assertTrue(tree.pairs().get(0).is(Tree.Kind.PAIR));
//    assertTrue(tree.pairs().get(1).is(Tree.Kind.PAIR));
//    assertTrue(tree.pairs().get(1).value().value().is(Tree.Kind.ARRAY));
//
//    tree = checkParsed(" { \"abc\": \"def\", \"zzz\" : {} }");
//    assertThat(tree.pairs()).hasSize(2);
//    assertTrue(tree.pairs().get(0).is(Tree.Kind.PAIR));
//    assertTrue(tree.pairs().get(1).is(Tree.Kind.PAIR));
//    assertTrue(tree.pairs().get(1).value().value().is(Tree.Kind.OBJECT));
//
//    tree = checkParsed(" { \"abc\": \"def\", \"zzz\" : { \"abc\": \"def\" ,\n \"zzz\" : 123 } , \"z\\\\\\\\z\\\"zz\": [ {}, {\"dd\": -12e+13} ]}");
//    assertThat(tree.pairs()).hasSize(3);
//    assertTrue(tree.pairs().get(0).is(Tree.Kind.PAIR));
//    assertTrue(tree.pairs().get(1).is(Tree.Kind.PAIR));
//    assertTrue(tree.pairs().get(2).is(Tree.Kind.PAIR));
//    assertTrue(tree.pairs().get(2).value().value().is(Tree.Kind.ARRAY));
//
//    tree = checkParsed(new File("src/test/resources/many-pairs.json"));
//    assertThat(tree.pairs()).hasSize(10000);
//  }
//
//  @Test
//  public void notObject() {
//    checkNotParsed("{");
//    checkNotParsed("}");
//    checkNotParsed("\"{}\"");
//  }
//
//  private ObjectTree checkParsed(String toParse) throws IOException {
//    ObjectTree tree = (ObjectTree) parser().parse(toParse);
//    assertThat(tree).isNotNull();
//    assertThat(tree.leftBrace()).isNotNull();
//    assertThat(tree.rightBrace()).isNotNull();
//    return tree;
//  }
//
//  private ObjectTree checkParsed(File file) throws IOException {
//    return checkParsed(Files.toString(file, Charsets.UTF_8));
//  }
//
//}
