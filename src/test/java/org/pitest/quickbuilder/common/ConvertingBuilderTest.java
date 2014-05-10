package org.pitest.quickbuilder.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.pitest.quickbuilder.Conversion;
import org.pitest.quickbuilder.builders.Builders;
import org.pitest.quickbuilder.common.ConvertingBuilder;

public class ConvertingBuilderTest {
  
  private ConvertingBuilder<Integer,Integer> testee;

  @Test
  public void shouldApplyConversionToAllValues() {
    testee = new ConvertingBuilder<Integer,Integer>(Builders.integersFrom(0).limit(3), timesTwo());
    
    assertThat(testee.buildAll()).containsExactly(0, 2, 4); 
  }
   
  @Test
  public void shouldLimitBuiltValues() {
    testee = new ConvertingBuilder<Integer,Integer>(Builders.integersFrom(0), timesTwo());
    
    assertThat(testee.limit(4).buildAll()).containsExactly(0, 2, 4, 6); 
  }
  
 
  @Test
  public void shouldIterateOverValues() {
    testee = new ConvertingBuilder<Integer,Integer>(Builders.integersFrom(2), timesTwo());
    
    assertThat(testee.iterator().next()).isEqualTo(4);
  }
  
  private Conversion<Integer, Integer> timesTwo() {
    return new Conversion<Integer, Integer>() {
      @Override
      public Integer convert(Integer a) {
        return a * 2;
      }
      
    };
  }

}
