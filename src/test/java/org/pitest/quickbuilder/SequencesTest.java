package org.pitest.quickbuilder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.pitest.quickbuilder.sequence.ElementSequence;

import com.example.beans.CompositeBean;
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

  public static class VeryComposite {

    private CompositeBean a;
    private CompositeBean b;
    private CompositeBean c;

    public CompositeBean getA() {
      return this.a;
    }

    public void setA(final CompositeBean a) {
      this.a = a;
    }

    public CompositeBean getB() {
      return this.b;
    }

    public void setB(final CompositeBean b) {
      this.b = b;
    }

    public CompositeBean getC() {
      return this.c;
    }

    public void setC(final CompositeBean c) {
      this.c = c;
    }

  }

  public interface VeryCompositeBuilder extends Builder<VeryComposite> {
    VeryCompositeBuilder withA(Builder<CompositeBean> v);

    VeryCompositeBuilder withB(Builder<CompositeBean> v);

    VeryCompositeBuilder withC(Builder<CompositeBean> v);
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
