package com.example.beans;

public class ArrayBean {

  private String[]  strings;

  private byte[]    bytes;

  private double[]  doubles;

  private boolean[] bools;

  public boolean[] getBools() {
    return this.bools;
  }

  public void setBools(final boolean[] bools) {
    this.bools = bools;
  }

  private boolean[][][][][] multi;

  public boolean[][][][][] getMulti() {
    return this.multi;
  }

  public void setMulti(final boolean[][][][][] multi) {
    this.multi = multi;
  }

  public byte[] getBytes() {
    return this.bytes;
  }

  public void setBytes(final byte[] bytes) {
    this.bytes = bytes;
  }

  public String[] getStrings() {
    return this.strings;
  }

  public void setStrings(final String[] strings) {
    this.strings = strings;
  }

  public double[] getDoubles() {
    return this.doubles;
  }

  public void setDoubles(final double[] doubles) {
    this.doubles = doubles;
  }

}
