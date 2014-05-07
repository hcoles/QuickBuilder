package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.pitest.quickbuilder.Maybe;
import org.pitest.quickbuilder.NoValueAvailableError;

public class NonBuilderTest {
  
  private NonBuilder<String> testee = new NonBuilder<String>();

  @Test(expected=NoValueAvailableError.class)
  public void shouldBuildNothing() {
    testee.build();
  }
  
  @Test
  public void shouldHaveNoNext() {
    assertThat(testee.next()).isEqualTo(Maybe.none());
  }
  
  @Test
  public void shouldBuildEmptyLists() {
    assertThat(testee.buildAll()).isEmpty();
    assertThat(testee.build(100)).isEmpty();
  }
  
  @Test
  public void shouldLimitToItself() {
    assertThat(testee.limit(42)).isEqualTo(testee);
  }

}
