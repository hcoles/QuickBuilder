package org.pitest.quickbuilder;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.pitest.quickbuilder.sequence.ElementSequence;

import com.example.beans.FruitBean;
import com.example.beans.FruitBuilder;

public class SequencesTest {
  
  
  @Test
  public void shouldProvideValuesInCorrectOrder() {
    FruitBuilder builder = QB.builder(FruitBuilder.class).withId(ElementSequence.from(Arrays.asList("one","two")));
    assertEquals("one",builder.build().getId());
    assertEquals("two",builder.build().getId());
  }

  @Test
  public void shouldBuildListsOfRequestedSize() {
    FruitBuilder builder = QB.builder(FruitBuilder.class).withId(ElementSequence.from(Arrays.asList("repeated","two","repeated")));
    List<FruitBean> expected = builder.build(2);
    assertThat(expected.size()).isEqualTo(2);
    assertThat(expected).haveExactly(1, fruitWithId("repeated")); 
    assertThat(expected).haveExactly(1, fruitWithId("two"));     
  }
  
  
  
  private Condition<FruitBean> fruitWithId(final String id) {
    return new Condition<FruitBean>() {
      @Override
      public boolean matches(FruitBean arg0) {
        return arg0.getId().equals(id);
      }
      
    };
  }
  
}
