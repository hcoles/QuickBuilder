package com.example.beans;

import org.pitest.quickbuilder.Builder;

public interface PrimitiveBeanBuilder extends Builder<PrimitiveBean> {

  PrimitiveBeanBuilder withI(int i);

  PrimitiveBeanBuilder withL(long l);

  PrimitiveBeanBuilder withF(float f);

  PrimitiveBeanBuilder withD(double f);

  PrimitiveBeanBuilder withS(short s);

  PrimitiveBeanBuilder withC(char c);

  PrimitiveBeanBuilder withB(boolean b);

  PrimitiveBeanBuilder withBy(byte by);

}
