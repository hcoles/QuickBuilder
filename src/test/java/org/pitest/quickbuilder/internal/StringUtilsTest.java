package org.pitest.quickbuilder.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringUtilsTest {

  @Test
  public void shouldParseCamelCaseStrings() {
    String expected[] = {"foo", "Bar", "Foo", "Bar"};
    assertThat(StringUtils.parseCamelCase("fooBarFooBar")).isEqualTo(expected);
  }
  
  @Test
  public void shouldProduceEmptyStringWhenRepeating0Times() {
    assertThat(StringUtils.repeat("foo", 0)).isEqualTo("");
  }
  
  @Test
  public void shouldProduceSuppliedStringWhenRepeating1Times() {
    assertThat(StringUtils.repeat("foo", 1)).isEqualTo("foo");
  }
  
  @Test
  public void shouldRepeatStrings() {
    assertThat(StringUtils.repeat("foo", 3)).isEqualTo("foofoofoo");
  }

}
