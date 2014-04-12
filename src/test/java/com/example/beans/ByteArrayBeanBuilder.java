package com.example.beans;

import org.pitest.quickbuilder.Builder;

public interface ByteArrayBeanBuilder extends Builder<ByteArrayBean>{
  ByteArrayBeanBuilder withBytes(byte[] bs);
}
