package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class ElementSequenceTest {
  
  private ElementSequence<String> testee;

  @Test
  public void shouldIterateThroughSuppliedValues() {
    testee = ElementSequence.from(Arrays.asList("a", "b", "c", "d"));
    assertThat(testee.build()).isEqualTo("a");
    assertThat(testee.next().value().build()).isEqualTo("b");
  }
  
}
