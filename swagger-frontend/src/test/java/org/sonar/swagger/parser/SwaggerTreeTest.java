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
	checkParsed(" ");
	//checkParsed("khbhb");
	checkParsed("swagger:\n  info: hhh");
	checkParsed("swagger:\n- \"rrr\"\n- \"sss\"");
	checkParsed("swagger:\n- \"rrrr\"\n- \'ssss\'");
	checkParsed("swagger: '2.0'\ninfo:\n  description: Orange FQC Traceability API definition\n  title: \"test\"");
	checkParsed("swagger: \"2.0\"\ninfo:\n  description: \"Orange FQC Traceability API definition\"\n  title: \"test\"");
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
*/
	//checkParsed(new File("src/test/resources/entry.yaml"));
	checkParsed(new File("src/test/resources/object.yaml"));
	//checkParsed(new File("src/test/resources/array.yaml"));
	//checkParsed(new File("src/test/resources/arrayOfObject.yaml"));
    //checkParsed(new File("src/test/resources/petstore.yaml"));
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
