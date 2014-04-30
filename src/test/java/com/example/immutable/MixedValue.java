package com.example.immutable;

import java.util.List;

public class MixedValue {

  private final String             s;
  private final String             ss;
  private final double[]           ds;
  private final float              f;
  private final List<List<String>> ls;

  public MixedValue(final String s, final String ss, final double[] ds,
      final float f, final List<List<String>> ls) {
    this.s = s;
    this.ss = ss;
    this.ds = ds;
    this.f = f;
    this.ls = ls;
  }

  public String getS() {
    return this.s;
  }

  public String getSs() {
    return this.ss;
  }

  public double[] getDs() {
    return this.ds;
  }

  public float getF() {
    return this.f;
  }

  public List<List<String>> getLs() {
    return this.ls;
  }

}
