package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class ElementSequenceTest {

  private ElementSequence<String> testee;

  @Test
  public void shouldIterateThroughSuppliedValues() {
    this.testee = ElementSequence.from(Arrays.asList("a", "b", "c", "d"));
    assertThat(this.testee.build()).isEqualTo("a");
    assertThat(this.testee.next().value().build()).isEqualTo("b");
  }
  
  @Test
  public void shouldBuildListsOfRequestedSize() {
    this.testee = ElementSequence.from(Arrays.asList("a", "b", "c", "d"));
    assertThat(this.testee.build(2)).containsExactly("a","b");
  }
  
  @Test
  public void shouldBuildAllAvailableValues() {
    this.testee = ElementSequence.from(Arrays.asList("a", "b", "c"));
    assertThat(this.testee.buildAll()).containsExactly("a","b","c");
  }
  
  @Test
  public void shouldLimitAvailableValues() {
    this.testee = ElementSequence.from(Arrays.asList("a", "b", "c"));
    assertThat(this.testee.limit(2).buildAll()).hasSize(2);
  }

}
