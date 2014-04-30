package com.example.example;

public class Banana extends Fruit {
  public final double curve;

  public Banana(final double curve) {
    this.curve = curve;
  }

  public double curve() {
    return this.curve;
  }
}
