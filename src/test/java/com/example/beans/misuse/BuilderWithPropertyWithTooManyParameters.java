package com.example.beans.misuse;

import org.pitest.quickbuilder.Builder;

import com.example.beans.primitives.PrimitiveBean;

public interface BuilderWithPropertyWithTooManyParameters extends
    Builder<PrimitiveBean> {

  BuilderWithPropertyWithTooManyParameters withI(int i, int j);

}
