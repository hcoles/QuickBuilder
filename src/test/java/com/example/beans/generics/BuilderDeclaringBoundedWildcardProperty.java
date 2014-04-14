package com.example.beans.generics;

import org.pitest.quickbuilder.Builder;

import com.example.beans.CompositeBean;
import com.example.beans.FruitBean;

public interface BuilderDeclaringBoundedWildcardProperty extends Builder<CompositeBean> {
  BuilderDeclaringBoundedWildcardProperty withMoreFruit(Builder<? super FruitBean> fb);
}
