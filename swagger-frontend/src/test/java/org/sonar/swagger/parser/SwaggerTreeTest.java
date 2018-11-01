package org.sonar.swagger.parser;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;
import org.sonar.plugins.swagger.api.tree.SwaggerTree;

import java.io.File;
import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

public class SwaggerTreeTest extends CommonSwaggerTreeTest {

  public SwaggerTreeTest() {
    super(SwaggerLexicalGrammar.SWAGGER);
  }

  @Test
  public void swagger() throws IOException {
	checkParsed("swagger:\ntitle: \"hsfhsd shfshh        \nsdhfshdf\"");
	/*checkParsed(" {   } ");
    checkParsed(" { \"abc\": \"def\" }");
    checkParsed(" { \"abc\": \"def\", \"zzz\" : \"123\" }");
	checkParsed(" {} ");
    checkParsed(" {   } ");
    checkParsed(" { \"abc\": \"def\" }");
    checkParsed(" { \"abc\": \"def\", \"zzz\" : \"123\" }");
    checkParsed(" { \"abc\": \"def\", \"zzz\" : [] }");
    checkParsed(" { \"abc\": \"def\", \"zzz\" : {} }");
    checkParsed(" { \"abc\": \"def\", \"zzz\" : { \"abc\": \"def\" , \"zzz\" : true } }");
    checkParsed(" { \"abc\": \"def\", \"zzz\" : { \"abc\": \"def\" , \"zzz\" : null } }");
    checkParsed(" { \"abc\": \"def\", \"zzz\" : { \"abc\": \"def\" , \"zzz\" : 123 } , \"zzzz\": [ {}, {\"dd\": -12e+13} ]}");
    checkParsed(" { \"abc\": \"def\", \"zzz\" : { \"abc\": \"def\" , \"zzz\" : 123 } ,\n \"z\\\\\\\\z\\\"zz\": [ {}, {\"dd\": \n -12e+13} ]}");
    checkParsed("[]");
    checkParsed(" []");
    checkParsed(" [   ] ");
    checkParsed(" [ true, \"abc\" ] ");
    checkParsed(" [ {}, true ] ");
    checkParsed("null");
    checkParsed(" null ");
    checkParsed("true");
    checkParsed(" true ");
    checkParsed("false");
    checkParsed(" false ");
    checkParsed("\"abc\"");
    checkParsed(" \"abc\" ");
    checkParsed("1.2");
    checkParsed(" 1.2 ");
    checkParsed("\ufeff");
    checkParsed("\ufeff ");
    checkParsed("\ufeff {}");
    checkParsed("\ufeff true");

    checkParsed(new File("src/test/resources/many-pairs.json"));
    checkParsed(new File("src/test/resources/many-values.json"));*/
  }

  @Test
  public void notSwagger() {
	  /*checkNotParsed("{");
    checkNotParsed("}");
    checkNotParsed("blabla");
    checkNotParsed("\"abc\": 10");
    checkNotParsed("true, false");*/
  }

  private void checkParsed(String toParse) throws IOException {
	SwaggerTree tree = (SwaggerTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
  }

  private void checkParsed(File file) throws IOException {
    checkParsed(Files.toString(file, Charsets.UTF_8));
  }

}
