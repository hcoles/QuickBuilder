package com.example.beans.primitives;

import org.pitest.quickbuilder.Builder;

public interface FloatBeanBuilder extends Builder<FloatBean> {
  FloatBeanBuilder withF(float f);
}
