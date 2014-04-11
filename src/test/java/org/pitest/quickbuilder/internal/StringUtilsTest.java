package org.pitest.quickbuilder.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringUtilsTest {

  @Test
  public void shouldParseCamelCaseStrings() {
    String expected[] = {"foo", "Bar", "Foo", "Bar"};
    assertThat(StringUtils.parseCamelCase("fooBarFooBar")).isEqualTo(expected);
  }

}
