package com.example.immutable;

import java.util.List;

import org.pitest.quickbuilder.Builder;

public interface MixedValueBuilder extends Builder<MixedValue> {

  
  MixedValueBuilder withS(String s);
  MixedValueBuilder withSs(String ss);
  MixedValueBuilder withDs(double[] ds);
  MixedValueBuilder withF(float f);
  MixedValueBuilder withLs(List<List<String>> ls);
  
  String _S();
  String _Ss();
  double[] _Ds();
  float _F();
  List<List<String>> _Ls();
  
}
