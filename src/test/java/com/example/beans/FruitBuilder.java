package com.example.beans;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.SequenceBuilder;

public interface FruitBuilder extends SequenceBuilder<FruitBean> {

  FruitBuilder withName(String name);

  FruitBuilder andColour(String colour);

  FruitBuilder withRipeness(int ripeness);

  FruitBuilder withId(String id);
  
  FruitBuilder withId(Builder<String> ids);
  
  FruitBuilder withFoo(String id);
  
  String _Foo();

}
