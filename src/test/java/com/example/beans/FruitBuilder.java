package com.example.beans;

import org.pitest.quickbuilder.Builder;

public interface FruitBuilder extends Builder<FruitBean> {

  FruitBuilder withName(String name);

  FruitBuilder andColour(String name);

  FruitBuilder withRipeness(int ripeness);

}
