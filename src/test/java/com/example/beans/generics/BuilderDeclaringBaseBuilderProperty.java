package com.example.beans.generics;

import org.pitest.quickbuilder.Builder;

import com.example.beans.CompositeBean;
import com.example.beans.FruitBean;

public interface BuilderDeclaringBaseBuilderProperty  extends Builder<CompositeBean>  {
	BuilderDeclaringBaseBuilderProperty withMoreFruit(Builder<FruitBean> fb);
}
