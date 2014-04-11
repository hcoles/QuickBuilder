package com.example.immutable;

import org.pitest.quickbuilder.Builder;

public interface IntegerValueBuilder extends Builder<IntegerValue> {

  IntegerValueBuilder withI(int i);

  int _I();

}
