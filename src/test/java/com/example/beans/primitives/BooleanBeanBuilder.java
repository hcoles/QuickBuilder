package com.example.beans.primitives;

import org.pitest.quickbuilder.Builder;

public interface BooleanBeanBuilder extends Builder<BooleanBean> {

  BooleanBeanBuilder withB(boolean b);
}
