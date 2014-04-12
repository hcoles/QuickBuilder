package com.example.beans;

import org.pitest.quickbuilder.Builder;

public interface StringBeanBuilder extends Builder<StringBean> {
  StringBeanBuilder withName(String s);
}
