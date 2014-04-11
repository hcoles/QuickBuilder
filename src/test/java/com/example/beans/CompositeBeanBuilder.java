package com.example.beans;

import org.pitest.quickbuilder.Builder;

public interface CompositeBeanBuilder extends Builder<CompositeBean> {

  CompositeBeanBuilder withFruit(FruitBuilder fb);
  CompositeBeanBuilder withFruit(FruitBean fb);  
}
