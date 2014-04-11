package com.example.beans;

import java.util.List;

import org.pitest.quickbuilder.Builder;

public interface GenericPropertiesBeanBuilder extends
    Builder<GenericPropertiesBean> {

  GenericPropertiesBeanBuilder withS(List<String> s);

  GenericPropertiesBeanBuilder withN(List<? extends Number> s);

}
