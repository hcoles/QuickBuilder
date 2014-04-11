package com.example.beans.misuse;

import org.pitest.quickbuilder.Builder;

import com.example.beans.CompositeBean;
import com.example.beans.CompositeBeanBuilder;
import com.example.beans.FruitBean;

public interface BuilderDeclaringBaseBuilderProperty  extends Builder<CompositeBean>  {
  // use of raw Builder<X> interface not supported
  CompositeBeanBuilder withMoreFruit(Builder<FruitBean> fb);
}
