package com.example.beans.misuse;

import org.pitest.quickbuilder.Builder;

public interface BuilderWithTypeMismatchInUnderscoreMethod extends Builder<String> {
  
  BuilderWithTypeMismatchInUnderscoreMethod withFoo(int i);
  
  long _Foo();

}