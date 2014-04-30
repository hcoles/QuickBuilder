package org.pitest.quickbuilder;

import java.util.ArrayList;
import java.util.List;

public class Sequences {
  
  public static  <T> List<T> buildAll(Builder<T> b) {
    List<T> ts = new ArrayList<T>();
    Maybe<Builder<T>> next = Maybe.some(b);
    while ( next.hasSome() ) {
      ts.add(next.value().build());
      next = next.value().next();
    }
    return ts;  
  }
  
}
