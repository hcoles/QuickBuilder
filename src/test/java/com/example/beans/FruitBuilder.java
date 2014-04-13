package com.example.beans;

import org.pitest.quickbuilder.Builder;

public interface FruitBuilder extends Builder<FruitBean> {

  FruitBuilder withName(String name);

  FruitBuilder andColour(String colour);

  FruitBuilder withRipeness(int ripeness);

  FruitBuilder withId(String id);

}
