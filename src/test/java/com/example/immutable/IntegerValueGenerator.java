package com.example.immutable;

import org.pitest.quickbuilder.Generator;

public class IntegerValueGenerator implements
    Generator<IntegerValueBuilder,IntegerValue> {

  @Override
  public IntegerValue generate(final IntegerValueBuilder builder) {
    return new IntegerValue(builder._I());
  }

}
