package com.example.example;

public class Apple extends Fruit {
  private final int leaves;

  public Apple(final int leaves) {
    this.leaves = leaves;
  }

  public int numberOfLeaves() {
    return this.leaves;
  }
}
