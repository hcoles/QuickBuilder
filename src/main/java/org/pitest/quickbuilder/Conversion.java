package org.pitest.quickbuilder;

public interface Conversion<A, B> {
  
  B convert(A a);

}
