package com.example.beans.misuse;

import org.pitest.quickbuilder.Builder;

public interface BuilderWithParameterisedUnderscoreMethod extends Builder<String> {
  
  BuilderWithParameterisedUnderscoreMethod withFoo(int i);
  
  int _Foo(int i);

}
