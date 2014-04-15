package com.example.beans;

import org.pitest.quickbuilder.Generator;
import org.pitest.quickbuilder.QB;

public class Fruits {
  
  static class Seed implements Generator<FruitBuilder,FruitBean> {
    @Override
    public FruitBean generate(FruitBuilder builder) {
      return new FruitBean();
    }
  }

  public static FruitBuilder aFruit() {
    return QB.builder(FruitBuilder.class, new Seed());
  }
  
}
