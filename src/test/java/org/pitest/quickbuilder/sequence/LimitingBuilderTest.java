package org.pitest.quickbuilder.sequence;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.pitest.quickbuilder.sequence.ElementSequence.from;

import java.util.List;

import org.junit.Test;
import org.pitest.quickbuilder.Builder;

public class LimitingBuilderTest {

  @Test
  public void shouldLimitSequences() {
    Builder<String> limited = LimitingBuilder.limit(3, from(asList("1","2","3","4")));
    List<String> actual = Sequences.buildAll(limited);
    
    assertThat(actual).containsExactly("1","2","3");
    
  }
  
  @Test
  public void shouldLimitInfiniteSequences() {
    Builder<String> limited = LimitingBuilder.limit(6, new ConstantBuilder<String>("a"));
    List<String> actual = Sequences.buildAll(limited);
    
    assertThat(actual).containsExactly("a", "a", "a", "a", "a", "a");
    
  }



}
