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
package org.sonar.swagger.checks.generic;

import org.junit.Test;
import org.sonar.swagger.checks.CheckTestUtils;
import org.sonar.swagger.checks.generic.VersionCheck;
import org.sonar.swagger.checks.util.SwaggerCheckVerifier;

public class ParsingErrorCheckTest {

	@Test
	public void should_follow_the_default_naming_convention_and_not_raise_an_issue() {
		SwaggerCheckVerifier.verify(new VersionCheck(), CheckTestUtils.getTestFile("test-file.yaml")).noMore();
	}
	
}
