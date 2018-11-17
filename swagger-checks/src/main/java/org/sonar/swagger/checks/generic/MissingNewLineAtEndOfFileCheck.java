/*
 * SonarQube JSON Analyzer
 * Copyright (C) 2015-2017 David RACODON
 * david.racodon@gmail.com
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
package org.sonar.swagger.checks.generic;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.swagger.checks.Tags;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;
import org.sonar.plugins.swagger.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "empty-line-end-of-file",
  name = "Files should contain an empty new line at the end",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleConstantRemediation("1min")
@ActivatedByDefault
public class MissingNewLineAtEndOfFileCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitSwagger(SwaggerTree tree) {
    try (RandomAccessFile randomAccessFile = new RandomAccessFile(getContext().getFile(), "r")) {
      if (!endsWithNewline(randomAccessFile)) {
        addFileIssue("Add an empty new line at the end of this file.");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Check json:" + this.getClass().getAnnotation(Rule.class).key()
        + ": Error while reading " + getContext().getFile().getName(), e);
    }
    super.visitSwagger(tree);
  }

  private static boolean endsWithNewline(RandomAccessFile randomAccessFile) throws IOException {
    if (randomAccessFile.length() < 1) {
      return false;
    }
    randomAccessFile.seek(randomAccessFile.length() - 1);
    byte[] chars = new byte[1];
    if (randomAccessFile.read(chars) < 1) {
      return false;
    }
    String ch = new String(chars);
    return "\n".equals(ch) || "\r".equals(ch);
  }

}
