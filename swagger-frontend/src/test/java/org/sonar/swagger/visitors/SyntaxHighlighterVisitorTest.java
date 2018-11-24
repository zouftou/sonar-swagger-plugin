package org.sonar.swagger.visitors;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.tree.Tree;
import org.sonar.plugins.swagger.api.visitors.TreeVisitorContext;
import org.sonar.swagger.parser.SwaggerParserBuilder;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.sonar.api.batch.sensor.highlighting.TypeOfText.*;

public class SyntaxHighlighterVisitorTest {

  private SyntaxHighlighterVisitor highlighterVisitor;
  private SensorContextTester sensorContext;
  private File file;
  private DefaultInputFile inputFile;
  private TreeVisitorContext visitorContext;

  @Rule
  public final TemporaryFolder tempFolder = new TemporaryFolder();

  @Before
  public void setUp() throws IOException {
    DefaultFileSystem fileSystem = new DefaultFileSystem(tempFolder.getRoot());
    fileSystem.setEncoding(Charsets.UTF_8);
    file = tempFolder.newFile();
    inputFile = new DefaultInputFile("moduleKey", file.getName())
      .setLanguage("json")
      .setType(InputFile.Type.MAIN);
    fileSystem.add(inputFile);

    sensorContext = SensorContextTester.create(tempFolder.getRoot());
    sensorContext.setFileSystem(fileSystem);
    visitorContext = mock(TreeVisitorContext.class);
    highlighterVisitor = new SyntaxHighlighterVisitor(sensorContext);
    when(visitorContext.getFile()).thenReturn(file);
  }

  @Test
  public void empty_input() throws Exception {
    highlight("");
    assertThat(sensorContext.highlightingTypeAt("moduleKey:" + file.getName(), 1, 0)).isEmpty();
  }

  @Test
  public void truee() throws Exception {
    highlight("true");
    assertHighlighting(1, 0, 4, CONSTANT);
  }

  @Test
  public void falsee() throws Exception {
    highlight("false");
    assertHighlighting(1, 0, 5, CONSTANT);
  }

  @Test
  public void nulle() throws Exception {
    highlight("null");
    assertHighlighting(1, 0, 4, CONSTANT);
  }

  @Test
  public void number() throws Exception {
    highlight("1.3e-1");
    assertHighlighting(1, 0, 6, CONSTANT);
  }

  @Test
  public void string() throws Exception {
    highlight("\"blabla\"");
    assertHighlighting(1, 0, 8, STRING);
  }

  @Test
  public void key() throws Exception {
    highlight("{\"blabla\": 2}");
    assertHighlighting(1, 1, 8, KEYWORD);
    assertHighlighting(1, 11, 1, CONSTANT);
  }

  @Test
  public void byte_order_mark() throws Exception {
    highlight("\ufefftrue");
    assertHighlighting(1, 0, 4, CONSTANT);
  }

  private void highlight(String string) throws Exception {
    inputFile.initMetadata(string);
    Tree tree = SwaggerParserBuilder.createParser(Charsets.UTF_8).parse(string);
    when(visitorContext.getTopTree()).thenReturn((SwaggerTree) tree);

    Files.write(string, file, Charsets.UTF_8);
    highlighterVisitor.scanTree(visitorContext);
  }

  private void assertHighlighting(int line, int column, int length, TypeOfText type) {
    for (int i = column; i < column + length; i++) {
      List<TypeOfText> typeOfTexts = sensorContext.highlightingTypeAt("moduleKey:" + file.getName(), line, i);
      assertThat(typeOfTexts).hasSize(1);
      assertThat(typeOfTexts.get(0)).isEqualTo(type);
    }
  }

}
