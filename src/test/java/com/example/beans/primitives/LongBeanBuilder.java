package com.example.beans.primitives;

import org.pitest.quickbuilder.Builder;

public interface LongBeanBuilder extends Builder<LongBean> {
  LongBeanBuilder withL(long l);
}
