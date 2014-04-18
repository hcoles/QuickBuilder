package org.pitest.quickbuilder;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.pitest.quickbuilder.sequence.ElementSequence;

import com.example.beans.FruitBuilder;

public class SequencesTest {
  
  @Test
  public void shouldProvideValuesInCorrectOrder() {
    FruitBuilder builder = QB.builder(FruitBuilder.class).withId(ElementSequence.from(Arrays.asList("one","two")));
    assertEquals("one",builder.build().getId());
    assertEquals("two",builder.build().getId());
  }

}
