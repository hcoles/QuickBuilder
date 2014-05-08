package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.pitest.quickbuilder.QuickBuilderError;

public class IntegersTest {
  
  private Integers testee;

  @Test
  public void shouldReturnIntegersFromSuppliedStart() {
    testee = Integers.integersFrom(0);
    assertThat(testee.build()).isEqualTo(0);
  }
  
  @Test
  public void shouldCreateSequencesOfIntegers() {
    testee = Integers.integersFrom(1);
    assertThat(testee.build(3)).containsExactly(1,2,3);
  }
  
  @Test
  public void shouldLimitSequence() {
    testee = Integers.integersFrom(1);
    assertThat(testee.limit(2).build(3)).containsExactly(1,2);
  }  
  
  @Test(expected=QuickBuilderError.class)
  public void shouldThrowErrorIfUserTriesToBuildObviouslyInfiniteSequence() {
    Integers.integersFrom(1).buildAll();
  }  
  
  @Test
  public void shouldLoopWhenMaxValueReached() {
    testee = Integers.integersFrom(Integer.MAX_VALUE);
    assertThat(testee.build(2)).containsExactly(Integer.MAX_VALUE,Integer.MIN_VALUE);
  } 
  
}
