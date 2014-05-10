package org.pitest.quickbuilder.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.pitest.quickbuilder.common.ConstantBuilder;
import org.pitest.quickbuilder.common.Sequences;

public class ConstantBuilderTest {

  @Test
  public void shouldAlwaysReturnSuppliedValue() {
    ConstantBuilder<String> testee = new ConstantBuilder<String>("foo");
    assertThat(Sequences.build(testee, 3)).containsOnly("foo","foo","foo");
  }

}
