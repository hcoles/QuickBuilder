package com.example.beans.misuse;

import org.pitest.quickbuilder.Builder;

import com.example.beans.primitives.PrimitiveBean;

public interface BuilderWithPropertyReturningWrongType extends Builder<PrimitiveBean> {

  int withI();
  
}
