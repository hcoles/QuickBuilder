package com.example.immutable;

import java.util.List;

public class MixedValue {
  
  private final String s;
  private final String ss;
  private final double[] ds;
  private final float f;
  private final List<List<String>> ls;
  
  public MixedValue(String s, String ss, double[] ds, float f, List<List<String>> ls) {
    this.s = s;
    this.ss = ss;
    this. ds =ds;
    this.f =f;
    this.ls = ls;
  }

  public String getS() {
    return s;
  }

  public String getSs() {
    return ss;
  }

  public double[] getDs() {
    return ds;
  }

  public float getF() {
    return f;
  }

  public List<List<String>> getLs() {
    return ls;
  }
  
  

}
