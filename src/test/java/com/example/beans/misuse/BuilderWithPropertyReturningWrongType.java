package com.example.beans.misuse;

import org.pitest.quickbuilder.Builder;

import com.example.beans.PrimitiveBean;

public interface BuilderWithPropertyReturningWrongType extends Builder<PrimitiveBean> {

  int withI();
  
}
