package com.example.immutable;

import org.pitest.quickbuilder.Generator;

public class IntegerValueGenerator implements
    Generator<IntegerValue, IntegerValueBuilder> {

  @Override
  public IntegerValue generate(final IntegerValueBuilder builder) {
    return new IntegerValue(builder._I());
  }

}
