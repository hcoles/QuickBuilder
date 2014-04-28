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
    assertThat(testee.build()).isEqualTo("b");
    assertThat(testee.build()).isEqualTo("c");
    assertThat(testee.build()).isEqualTo("d");
  }
  
  @Test
  public void shouldCreateNonInterferingCopies() {
    testee = ElementSequence.from(Arrays.asList("a", "b"));
    ElementSequence<String> copy = testee.but();
    assertThat(copy.build()).isEqualTo("a");
    assertThat(copy.build()).isEqualTo("b");
    assertThat(testee.build()).isEqualTo("a");
    assertThat(testee.build()).isEqualTo("b");
  }
  
  @Test
  public void shouldReportNumberOfAvailableValues() {
    testee = ElementSequence.from(Arrays.asList("a", "b"));
    assertThat(testee.valueLimit()).isEqualTo(2);
    testee.build();
    assertThat(testee.valueLimit()).isEqualTo(1);
    testee.build();
    assertThat(testee.valueLimit()).isEqualTo(0);    
  }  

}
