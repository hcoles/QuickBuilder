package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.pitest.quickbuilder.sequence.ConstantBuilder.constant;

import org.junit.Test;

public class RepeatedBuilderTest {
  
  private SequenceBuilder<String> testee;

  @Test
  public void shouldReturnTheSameValue() {
    testee = RepeatedBuilder.repeat(2,constant("foo"));
    assertThat(testee.buildAll()).containsExactly("foo", "foo"); 
    assertThat(testee.build(3)).containsExactly("foo", "foo"); 
  }
  
  @Test
  public void shouldLimitTheRepeatedValues() {
    testee = RepeatedBuilder.repeat(20,constant("foo"));
    assertThat(testee.limit(1).buildAll()).containsExactly("foo"); 
  }
  
  @Test
  public void shouldBuildNothingWhen0RepeatsRequested() {
    testee = RepeatedBuilder.repeat(0,constant("foo"));
    assertThat(testee.buildAll()).isEmpty();
    assertThat(testee.build(2)).isEmpty();
  }  

  @Test
  public void shouldIterateOverValues() {
    testee = RepeatedBuilder.repeat(2,constant("foo"));
    assertThat(testee.iterator().next()).isEqualTo("foo");
  }
}
