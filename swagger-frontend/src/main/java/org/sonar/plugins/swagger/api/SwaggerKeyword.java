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
  DEFINITIONS("definitions"),
  PARAMETERS("parameters"),
  RESPONSES("responses"),
  SECURITY_DEFINITIONS("securityDefinitions"),
  SECURITY("security"),
  TAGS("tags"),
  EXTERNAL_DOCS("externalDocs"),
  TRUE("true"),
  FALSE("false"),
  NULL("null");

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
