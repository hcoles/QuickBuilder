package com.example.immutable;

import org.pitest.quickbuilder.Generator;

public class MixedValueGenerator implements Generator<MixedValueBuilder,MixedValue> {

  @Override
  public MixedValue generate(MixedValueBuilder b) {
    return new MixedValue(b._S(), b._Ss(), b._Ds(), b._F(), b._Ls());
  }

}
