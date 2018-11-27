/*
 * SonarQube Swagger Analyzer
 * Copyright (C) 2015-2017 Zouhir OUFTOU
 * zouhir.ouftou@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
      .setLanguage("swagger")
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
    highlight("test: true");
    assertHighlighting(1, 6, 4, CONSTANT);
  }

  @Test
  public void falsee() throws Exception {
    highlight("test: false");
    assertHighlighting(1, 6, 5, CONSTANT);
  }

  @Test
  public void nulle() throws Exception {
    highlight("test: null");
    assertHighlighting(1, 6, 4, CONSTANT);
  }

  @Test
  public void number() throws Exception {
    highlight("test: 1.3e-1");
    assertHighlighting(1, 6, 6, CONSTANT);
  }

  @Test
  public void string() throws Exception {
    highlight("test: \"blabla\"");
    assertHighlighting(1, 6, 8, STRING);
  }

  @Test
  public void key() throws Exception {
    highlight("test: 89");
    assertHighlighting(1, 0, 4, KEYWORD);
    assertHighlighting(1, 6, 1, CONSTANT);
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
