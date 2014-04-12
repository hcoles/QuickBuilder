package com.example.beans.primitives;

import org.pitest.quickbuilder.Builder;

public interface IntBeanBuilder extends Builder<IntBean> {
  
  IntBeanBuilder withI(int i);

}
