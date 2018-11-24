package org.sonar.swagger.parser;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.sonar.swagger.tree.impl.InternalSyntaxToken;
import org.sonar.swagger.tree.impl.SeparatedList;

import java.util.Arrays;
import java.util.Collections;

import static org.fest.assertions.Assertions.assertThat;

public class SeparatedListTest {

  @Test
  public void validSeparatorList() {
    String[] elements = {"a", "b", "c"};
    InternalSyntaxToken[] separators = {
      new InternalSyntaxToken(1, 1, ",", false, false),
      new InternalSyntaxToken(2, 1, ",", false, false)
    };
    SeparatedList<String> separatedList = new SeparatedList<>(Arrays.asList(elements), Arrays.asList(separators));

    assertThat(separatedList).isNotEmpty();
    assertThat(separatedList.get(0)).isEqualTo("a");
    assertThat(separatedList.get(1)).isEqualTo("b");
    assertThat(separatedList.get(2)).isEqualTo("c");

    assertThat(separatedList).contains("a");
    assertThat(separatedList).containsOnly("a", "b", "c");
    assertThat(separatedList.containsAll(ImmutableList.of("a", "b", "c"))).isTrue();

    assertThat(separatedList.getSeparators()).hasSize(2);
    assertThat(separatedList.getSeparators().get(0).text()).isEqualTo(",");
    assertThat(separatedList.getSeparators().get(1).text()).isEqualTo(",");
  }

  @Test
  public void emptySeparatedList() {
    SeparatedList<String> separatedList = new SeparatedList<>(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
    assertThat(separatedList).isEmpty();
    assertThat(separatedList.getSeparators()).isEmpty();
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidSeparatedList() {
    String[] elements = {"a", "b", "c"};
    InternalSyntaxToken[] separators = {
      new InternalSyntaxToken(1, 1, ",", false, false)
    };
    new SeparatedList<>(Arrays.asList(elements), Arrays.asList(separators));
  }

}
