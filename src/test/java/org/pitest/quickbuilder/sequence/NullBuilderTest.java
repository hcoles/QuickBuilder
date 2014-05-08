package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

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
