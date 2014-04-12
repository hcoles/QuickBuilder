package com.example.beans;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.internal.StoredValueBuilder;

public class ArrayBeanBuilderImpl implements ArrayBeanBuilder {

  private Builder<byte[]> ba; 
  
  @Override
  public ArrayBean build() {
    ArrayBean b = new ArrayBean();
    b.setBytes(ba.build());
    return b;
  }

  @Override
  public Builder<ArrayBean> but() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayBeanBuilder withBytes(byte[] bs) {
    this.ba = new StoredValueBuilder<byte[]>(bs);
    return this;
  }

  @Override
  public ArrayBeanBuilder withStrings(String[] strings) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayBeanBuilder withDoubles(double[] doubles) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayBeanBuilder withBools(boolean[] bools) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayBeanBuilder withMulti(boolean[][][][][] multi) {
    // TODO Auto-generated method stub
    return null;
  }

}
