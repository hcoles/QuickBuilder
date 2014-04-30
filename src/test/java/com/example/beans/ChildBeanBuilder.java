package com.example.beans;

import org.pitest.quickbuilder.Builder;

public interface ChildBeanBuilder extends Builder<ChildBean> {

  ChildBeanBuilder withFoo(String foo);

  ChildBeanBuilder withBar(String bar);

}
