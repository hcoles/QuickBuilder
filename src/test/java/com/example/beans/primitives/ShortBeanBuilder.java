package com.example.beans.primitives;

import org.pitest.quickbuilder.Builder;

public interface ShortBeanBuilder extends Builder<ShortBean>{
  ShortBeanBuilder withS(short s);
}
