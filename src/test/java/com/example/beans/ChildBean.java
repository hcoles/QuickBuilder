package com.example.beans;

public class ChildBean extends BaseBean {

  @Override
  public void setFoo(final String foo) {
    super.setFoo("modifiedbychild_" + foo);
  }

}
