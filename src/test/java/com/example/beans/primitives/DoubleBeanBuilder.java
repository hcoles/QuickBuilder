package com.example.beans.primitives;

import org.pitest.quickbuilder.Builder;

public interface DoubleBeanBuilder extends Builder<DoubleBean> {
  DoubleBeanBuilder withD(double d);
}
