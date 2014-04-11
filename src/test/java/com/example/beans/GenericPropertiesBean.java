package com.example.beans;

import java.util.List;

public class GenericPropertiesBean {

  private List<String>           s;
  private List<? extends Number> n;

  public List<? extends Number> getN() {
    return this.n;
  }

  public void setN(final List<? extends Number> n) {
    this.n = n;
  }

  public List<String> getS() {
    return this.s;
  }

  public void setS(final List<String> s) {
    this.s = s;
  }

}
