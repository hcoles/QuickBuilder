package org.pitest.quickbuilder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.beans.MutableFruitBuilder;

public class MutableBuilderTest {

  @Test
  public void shouldImplementButMethodThatReturnsCopyOfTheBuilder() {
    final MutableFruitBuilder builder = QB.builder(MutableFruitBuilder.class);
    assertThat(builder.but()).isNotSameAs(builder);
  }
  
  @Test
  public void shouldUpdateStateOfBuilderObjectWhenWithMethodCalled() {
    final MutableFruitBuilder builder = QB.builder(MutableFruitBuilder.class);
    final MutableFruitBuilder original = builder.withName("original");
    assertThat(builder.withName("foo").build().getName()).isEqualTo("foo");
    assertThat(original.build().getName()).isEqualTo("foo");
  }
  
}
