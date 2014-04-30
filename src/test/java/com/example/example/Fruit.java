package com.example.example;

public abstract class Fruit {
  private double ripeness = 0.0;

  public void ripen(final double amount) {
    this.ripeness = Math.min(1.0, this.ripeness + amount);
  }

  public boolean isRipe() {
    return this.ripeness == 1.0;
  }
}
