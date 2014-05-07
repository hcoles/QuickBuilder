package org.pitest.quickbuilder.sequence;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.pitest.quickbuilder.sequence.ElementSequence.from;

import java.util.List;

import org.junit.Test;

public class LimitingBuilderTest {

  @Test
  public void shouldLimitSequences() {
    SequenceBuilder<String> limited = LimitingBuilder.limit(3, from(asList("1","2","3","4")));
    List<String> actual = limited.buildAll();
    
    assertThat(actual).containsExactly("1","2","3");
    
  }
  
  @Test
  public void shouldLimitInfiniteSequences() {
    SequenceBuilder<String> limited = LimitingBuilder.limit(6, new ConstantBuilder<String>("a"));
    assertThat(limited.buildAll()).containsExactly("a", "a", "a", "a", "a", "a");
    assertThat(limited.limit(2).buildAll()).containsExactly("a", "a"); 
  }
  
  @Test
  public void shouldBuildRequestedNumberOfValuesWithinLimit() {
    SequenceBuilder<String> limited = LimitingBuilder.limit(6, new ConstantBuilder<String>("a"));
    assertThat(limited.build(3)).containsExactly("a", "a", "a");
  }
  
  @Test
  public void shouldLimitToNothingWhenSuppliedWithLimitOfZero() {
    SequenceBuilder<String> limited = LimitingBuilder.limit(0, new ConstantBuilder<String>("a"));
    assertThat(limited.buildAll()).isEmpty();
    assertThat(limited.build(2)).isEmpty();
  }

  @Test
  public void shouldLimitToNothingWhenSuppliedWithLimitOfBelowZero() {
    SequenceBuilder<String> limited = LimitingBuilder.limit(-1, new ConstantBuilder<String>("a"));
    assertThat(limited.buildAll()).isEmpty();
    assertThat(limited.build(2)).isEmpty();
  }


}
