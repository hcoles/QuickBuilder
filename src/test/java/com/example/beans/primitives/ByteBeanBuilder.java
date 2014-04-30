package com.example.beans.primitives;

import org.pitest.quickbuilder.Builder;

public interface ByteBeanBuilder extends Builder<ByteBean> {

  ByteBeanBuilder withBy(byte b);

}
