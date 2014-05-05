package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.pitest.quickbuilder.sequence.ConstantBuilder.constant;

import org.junit.Test;
import org.pitest.quickbuilder.NoValueAvailableError;

public class RepeatedBuilderTest {
  
  private RepeatedBuilder<String> testee;

  @Test
  public void shouldReturnTheSameValue() {
    testee = RepeatedBuilder.repeat(2,constant("foo"));
    assertThat(Sequences.buildAll(testee)).containsExactly("foo", "foo"); 
  }
  
  @Test(expected=NoValueAvailableError.class)
  public void shouldThrowErrorIfLessThanOneRepeatRequested() {
    testee = RepeatedBuilder.repeat(0,constant("foo"));
    assertThat(Sequences.buildAll(testee)).isEmpty();
  }  

}
