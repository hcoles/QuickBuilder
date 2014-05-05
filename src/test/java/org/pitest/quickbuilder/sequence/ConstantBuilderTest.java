package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ConstantBuilderTest {

  @Test
  public void shouldAlwaysReturnSuppliedValue() {
    ConstantBuilder<String> testee = new ConstantBuilder<String>("foo");
    assertThat(Sequences.build(testee, 3)).containsOnly("foo","foo","foo");
  }

}
