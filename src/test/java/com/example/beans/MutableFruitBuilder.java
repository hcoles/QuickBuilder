package com.example.beans;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.MutableBuilder;

public interface MutableFruitBuilder extends MutableBuilder<FruitBean> {

  MutableFruitBuilder withName(String name);

  MutableFruitBuilder andColour(String colour);

  MutableFruitBuilder withRipeness(int ripeness);

  MutableFruitBuilder withId(Builder<String> id);

}
