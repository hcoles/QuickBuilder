package org.pitest.quickbuilder.sequence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.pitest.quickbuilder.Builder;
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
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        ElementSequence.from(Arrays.asList("repeated", "two", "repeated")));
    final List<FruitBean> expected = Sequences.build(builder, 2);
    assertThat(expected.size()).isEqualTo(2);
    assertThat(expected).haveExactly(1, fruitWithId("repeated"));
    assertThat(expected).haveExactly(1, fruitWithId("two"));
  }

  @Test
  public void shouldBuildListsOfRequestedSizeViaInstanceMethod() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class);
    final List<FruitBean> expected = builder.build(2);
    assertThat(expected.size()).isEqualTo(2);
  }

  @Test
  public void shouldConsumeAllOfSmallestSequencesWhenBuidAllCalledViaInstanceMethod() {
    final FruitBuilder builder = QB.builder(FruitBuilder.class).withId(
        ElementSequence.from(Arrays.asList("one", "two")));
    final List<FruitBean> actual = builder.buildAll();
    assertThat(actual).hasSize(2);
  }
  
  @Test
  public void shouldLimitSequences() {
    final SequenceBuilder<FruitBean> builder = QB.builder(FruitBuilder.class).limit(12);
    assertThat(builder.buildAll()).hasSize(12);
  }
  
  @Test
  public void shouldReturnValuesOnlyOnce() {
    final SequenceBuilder<String> builder = Sequences.once("foo");
    assertThat(builder.buildAll()).containsExactly("foo");
  }
  
  @Test
  public void shouldRepeatBuilders() {
    final SequenceBuilder<String> builder = Sequences.repeat(Sequences.constant("foo"), 2);
    assertThat(builder.buildAll()).containsExactly("foo", "foo");
  }

  @Test
  public void shouldReturnSameInstanceOnRepeatedRequests() {
    final Builder<FruitBean> builder = Sequences.theSame(QB.builder(FruitBuilder.class));
    assertThat(builder.build()).isSameAs(builder.build());
  }

  @Test
  public void shouldAllowUserToBuildNulls() {
    final SequenceBuilder<String> builder = Sequences.once(Sequences.<String>nullValue());
    assertThat(builder.build()).isNull();
  }
  
  @Test
  public void shouldAllowUserToBuildSequencesOfIntegers() {
    final SequenceBuilder<Integer> builder = Sequences.integersFrom(2);
    assertThat(builder.build(3)).containsExactly(2,3,4);
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
