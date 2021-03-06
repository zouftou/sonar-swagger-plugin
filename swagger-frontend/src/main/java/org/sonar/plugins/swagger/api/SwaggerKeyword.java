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
package org.sonar.plugins.swagger.api;

import org.sonar.sslr.grammar.GrammarRuleKey;

/**
 * Keywords for swagger grammar.
 */
public enum SwaggerKeyword implements GrammarRuleKey {

	SWAGGER("swagger"),
	INFO("info"),
	TITLE("title"),
	DESCRIPTION("description"),
	TERMS_OF_SERVICE("termsOfService"),
	CONTACT("contact"),
	NAME("name"),
	URL("url"),
	EMAIL("email"),
	LICENSE("license"),
	VERSION("version"),
	HOST("host"),
	BASE_PATH("basePath"),
	SCHEMES("schemes"),
	CONSUMES("consumes"),
	PRODUCES("produces"),
	PATHS("paths"),
	GET("get"),
	PUT("put"),
	POST("post"),
	DELETE("delete"),
	OPTIONS("options"),
	HEAD("head"),
	PATCH("patch"),
	SUMMARY("summary"),
	OPERATION_ID("operationId"),
	DEPRECATED("deprecated"),
	IN("in"),
	REQUIRED("required"),
	SCHEMA("schema"),
	TYPE("type"),
	FORMAT("format"),
	ALLOW_EMPTY_VALUE("allowEmptyValue"),
	ITEMS("items"),
	COLLECTION_FORMAT("collectionFormat"),
	DEFAULT("default"),
	MAXIMUM("maximum"),
	EXCLUSIVE_MAXIMUM("exclusiveMaximum"),
	MINIMUM("minimum"),
	EXCLUSIVE_MINIMUM("exclusiveMinimum"),
	MAX_LENGTH("maxLength"),
	MIN_LENGTH("minLength"),
	PATTERN("pattern"),
	MAX_ITEMS("maxItems"),
	MIN_ITEMS("minItems"),
	UNIQUE_ITEMS("uniqueItems"),
	ENUM("enum"),
	MULTIPLE_OF("multipleOf"),
	HEADERS("headers"),
	DEFINITIONS("definitions"),
	PARAMETERS("parameters"),
	RESPONSES("responses"),
	SECURITY_DEFINITIONS("securityDefinitions"),
	SECURITY("security"),
	TAGS("tags"),
	EXTERNAL_DOCS("externalDocs");

	private final String value;

	SwaggerKeyword(String value) {
		this.value = value;
	}

	public String getName() {
		return name();
	}

	public String getValue() {
		return value;
	}

	public static String[] keywordValues() {
		SwaggerKeyword[] keywordsEnum = SwaggerKeyword.values();
		String[] keywords = new String[keywordsEnum.length];
		for (int i = 0; i < keywords.length; i++) {
			keywords[i] = keywordsEnum[i].getValue();
		}
		return keywords;
	}

}
