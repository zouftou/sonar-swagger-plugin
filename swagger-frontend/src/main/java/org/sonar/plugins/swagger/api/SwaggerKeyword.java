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

  /**
   * keywords as String.
   * @return an array containing all keywords as typed in Swagger
   */
  public static String[] keywordValues() {
    SwaggerKeyword[] keywordsEnum = SwaggerKeyword.values();
    String[] keywords = new String[keywordsEnum.length];
    for (int i = 0; i < keywords.length; i++) {
      keywords[i] = keywordsEnum[i].getValue();
    }
    return keywords;
  }

}
