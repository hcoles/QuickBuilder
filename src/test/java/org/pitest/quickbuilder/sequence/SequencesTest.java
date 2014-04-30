package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.pitest.quickbuilder.QB;

import com.example.beans.FruitBean;
import com.example.beans.FruitBuilder;

public class SequencesTest {

  @Test
  public void shouldConsumeAllOfLimitedSequences() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        ElementSequence.from(Arrays.asList("one", "two")));
    final List<FruitBean> actual = Sequences.buildAll(builder);
    assertThat(actual).hasSize(2);
  }

  @Test
  public void shouldBuildListsOfRequestedSize() {
    FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        ElementSequence.from(Arrays.asList("repeated", "two", "repeated")));
    List<FruitBean> expected = Sequences.build(builder, 2);
    assertThat(expected.size()).isEqualTo(2);
    assertThat(expected).haveExactly(1, fruitWithId("repeated"));
    assertThat(expected).haveExactly(1, fruitWithId("two"));
  }

  @Test
  public void shouldBuildListsOfRequestedSizeViaInstanceMethod() {
    FruitBuilder builder = QB.builder(FruitBuilder.class);
    List<FruitBean> expected = builder.build(2);
    assertThat(expected.size()).isEqualTo(2);
  }
  
  @Test
  public void shouldConsumeAllOfSmallestSequencesWhenBuidAllCalledViaInstanceMethod() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        ElementSequence.from(Arrays.asList("one", "two")));
    final List<FruitBean> actual = builder.buildAll();
    assertThat(actual).hasSize(2);
  }
  
  private Condition<FruitBean> fruitWithId(final String id) {
    return new Condition<FruitBean>() {
      @Override
      public boolean matches(final FruitBean arg0) {
        return arg0.getId().equals(id);
      }

    };
  }

}
