package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.pitest.quickbuilder.sequence.ConstantBuilder.constant;
import static org.pitest.quickbuilder.builders.Builders.repeat;

import org.junit.Test;

public class SequenceWrapperTest {
  
  private SequenceWrapper<String> testee;

  @Test
  public void shouldBuildRequestedNumberOfValues() {
    testee = new SequenceWrapper<String>(repeat(constant("foo"),20));
    assertThat(testee.build(3)).containsExactly("foo","foo","foo");
  }
  
  @Test
  public void shouldBuildAllValues() {
    testee = new SequenceWrapper<String>(repeat(constant("foo"),2));
    assertThat(testee.buildAll()).containsExactly("foo","foo");
  }
  
  @Test
  public void shouldLimitTheRepeatedValues() {
    testee = new SequenceWrapper<String>(repeat(constant("foo"),20));
    assertThat(testee.limit(1).buildAll()).containsExactly("foo"); 
  }
  
  @Test
  public void shouldIterateOverValues() {
    testee = new SequenceWrapper<String>(repeat(constant("foo"),2));
    assertThat(testee.iterator().next()).isEqualTo("foo");
  }
}
