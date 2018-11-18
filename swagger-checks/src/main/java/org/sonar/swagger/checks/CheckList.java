/*
 * SonarQube Swagger Analyzer
 * Copyright (C) 2018-2020 Zouhir OUFTOU
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

import java.util.Collection;

import org.sonar.swagger.checks.generic.FileNameCheck;
import org.sonar.swagger.checks.generic.ParsingErrorCheck;

import com.google.common.collect.ImmutableList;

public final class CheckList {

	  public static final String REPOSITORY_KEY = "swagger";
	  public static final String REPOSITORY_NAME = "SonarQube";

	  private CheckList() {
	  }

	  @SuppressWarnings("rawtypes")
	  public static Collection<Class> getChecks() {
	    return ImmutableList.<Class>of(
	      FileNameCheck.class,
	      ParsingErrorCheck.class);
	  }
	  
	  @SuppressWarnings("rawtypes")
	  public static Collection<Class> getApiGuidelinesChecks() {
	    return ImmutableList.<Class>of(
	      FileNameCheck.class);
	  }
}
