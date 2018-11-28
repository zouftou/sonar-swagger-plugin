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
package org.sonar.swagger.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.swagger.api.SwaggerCheck;

public class CheckUtils {

	public static final String LINK_TO_JAVA_REGEX_PATTERN_DOC = "http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html";

	public static final String LINK_TO_SEMANTIC_VERSIONING_SPEC = "https://semver.org/spec/v2.0.0.html";
	
	private CheckUtils() {
	}

	public static String paramsErrorMessage(Class<? extends SwaggerCheck> clazz, String message) {
		return "Check swagger:" + clazz.getAnnotation(Rule.class).key() + " (" + clazz.getAnnotation(Rule.class).name()
				+ "): " + message;
	}
	
}
