package com.example.beans.primitives;

import org.pitest.quickbuilder.Builder;

public interface CharBeanBuilder extends Builder<CharBean> {
  CharBeanBuilder withC(char c);
}
