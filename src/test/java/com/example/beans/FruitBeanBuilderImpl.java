package com.example.beans;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.internal.StoredValueBuilder;

public class FruitBeanBuilderImpl implements FruitBuilder {
  
  private Builder<String> name;
  private Builder<Integer> ripeness;
  
  FruitBeanBuilderImpl(FruitBeanBuilderImpl o) {
    this.name = o.name;
    this.ripeness = o.ripeness;
  }
  
  @Override
  public FruitBean build() {
    FruitBean b = new FruitBean();
    b.setName(this.name.build());
    return b;
  }

  @Override
  public FruitBeanBuilderImpl but() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public FruitBuilder withName(String name) {
    this.name = new StoredValueBuilder<String>(name);
    return this;
  }

  @Override
  public FruitBuilder andColour(String name) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public String _Name() {
    return this.name.build();
  }

  @Override
  public FruitBuilder withRipeness(int ripeness) {
    this.ripeness = new StoredValueBuilder<Integer>(ripeness);
    return this;
  }

}
