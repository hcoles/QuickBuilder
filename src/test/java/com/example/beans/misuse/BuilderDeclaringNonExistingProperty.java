package com.example.beans.misuse;

import org.pitest.quickbuilder.Builder;

import com.example.beans.PrimitiveBean;

public interface BuilderDeclaringNonExistingProperty extends Builder<PrimitiveBean> {
  BuilderDeclaringNonExistingProperty withDoesNotExist(int i);
}
