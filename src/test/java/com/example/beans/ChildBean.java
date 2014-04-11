package com.example.beans;

public class ChildBean extends BaseBean {
  
  @Override
  public void setFoo(String foo) {
    super.setFoo("modifiedbychild_" + foo);
  }

}
