package com.example.beans;

public class CompositeBean {

  private FruitBean fruit;
  private FruitBean moreFruit;

  public FruitBean getFruit() {
    return this.fruit;
  }

  public void setFruit(final FruitBean fruit) {
    this.fruit = fruit;
  }

  public FruitBean getMoreFruit() {
    return this.moreFruit;
  }

  public void setMoreFruit(final FruitBean moreFruit) {
    this.moreFruit = moreFruit;
  }

}
