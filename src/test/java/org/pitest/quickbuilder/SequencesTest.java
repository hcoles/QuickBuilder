package org.pitest.quickbuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Condition;
import org.junit.Test;
import org.pitest.quickbuilder.sequence.ElementSequence;

import com.example.beans.CompositeBean;
import com.example.beans.CompositeBeanBuilder;
import com.example.beans.FruitBean;
import com.example.beans.FruitBuilder;
import com.example.immutable.MixedValueBuilder;
import com.example.immutable.MixedValueGenerator;

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
  
  @Test
  public void shouldReportInfiniteAvailableValuesForSimpleBuilder() {
    FruitBuilder builder = QB.builder(FruitBuilder.class);
    assertThat(builder.valueLimit()).isEqualTo(-1);
  }
  
  @Test
  public void shouldReportNoAvailableValuesWhenChildSequenceExhausted() {
    FruitBuilder builder = QB.builder(FruitBuilder.class).withId(ElementSequence.from(Arrays.asList("one","two")));
    builder.build(2);
    assertThat(builder.valueLimit()).isEqualTo(0);
  }
    
  public static class VeryComposite {
    
    private CompositeBean a;
    private CompositeBean b;
    private CompositeBean c;
    
    public CompositeBean getA() {
      return a;
    }
    public void setA(CompositeBean a) {
      this.a = a;
    }
    public CompositeBean getB() {
      return b;
    }
    public void setB(CompositeBean b) {
      this.b = b;
    }
    public CompositeBean getC() {
      return c;
    }
    public void setC(CompositeBean c) {
      this.c = c;
    }
        
  }
  
  public interface VeryCompositeBuilder extends SequenceBuilder<VeryComposite> {
    VeryCompositeBuilder withA(Builder<CompositeBean> v);
    VeryCompositeBuilder withB(Builder<CompositeBean> v);
    VeryCompositeBuilder withC(Builder<CompositeBean> v);
  }
  
  @Test
  public void shouldReportNoAvailableValuesWhenOnlyOneChildSequenceExhausted() {
    FruitBuilder fb = QB.builder(FruitBuilder.class).withId(ElementSequence.from(Arrays.asList("one","two")));
    CompositeBeanBuilder cb = QB.builder(CompositeBeanBuilder.class).withFruit(fb);
    
    // no value set for b
    VeryCompositeBuilder builder = QB.builder(VeryCompositeBuilder.class).withC(cb).withB(ElementSequence.from(Arrays.<CompositeBean>asList(null,null,null)));
    
    assertThat(builder.valueLimit()).isEqualTo(2);
    builder.build(2);
    assertThat(builder.valueLimit()).isEqualTo(0);
  }
      
  
  @Test
  public void willReportInifiniteLimitForUserHandledTypesWhenRequiredValueIsMissing() {
    final MixedValueBuilder builder = QB.builder(MixedValueBuilder.class,
        new MixedValueGenerator());
    // required values not set, but only user knows which values are required . . .
    assertThat(builder.valueLimit()).isEqualTo(-1);
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
