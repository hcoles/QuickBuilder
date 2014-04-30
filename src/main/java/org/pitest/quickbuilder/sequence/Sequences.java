package org.pitest.quickbuilder.sequence;

import java.util.ArrayList;
import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;

public class Sequences {

  public static <T> List<T> buildAll(final Builder<T> b) {
    final List<T> ts = new ArrayList<T>();
    Maybe<Builder<T>> next = Maybe.some(b);
    while (next.hasSome()) {
      ts.add(next.value().build());
      next = next.value().next();
    }
    return ts;
  }
  
  public static <T> List<T> build(final Builder<T> b, int number) {
    final List<T> ts = new ArrayList<T>();
    Maybe<Builder<T>> next = Maybe.some(b);
    int count = 0;
    while (next.hasSome() && count < number) {
      ts.add(next.value().build());
      next = next.value().next();
      count++;
    }
    return ts;
  }

}
