package org.pitest.quickbuilder.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.pitest.quickbuilder.common.NullBuilder;
import org.pitest.quickbuilder.common.Sequences;

public class NullBuilderTest {
  
  private NullBuilder<String> testee = new NullBuilder<String>();

  @Test
  public void shouldBuildNull() {
    assertThat(testee.build()).isNull();
  }

  @Test
  public void shouldAlwaysBuildNull() {
    assertThat(Sequences.build(testee,3)).containsExactly(null,null,null);
  }
}
