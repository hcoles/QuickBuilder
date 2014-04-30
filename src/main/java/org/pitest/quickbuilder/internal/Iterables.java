package org.pitest.quickbuilder.internal;

import java.util.ArrayList;
import java.util.List;

public class Iterables {

  public static <T> List<T> asList(final Iterable<T> ts) {
    final List<T> l = new ArrayList<T>(10);
    for (final T each : ts) {
      l.add(each);
    }
    return l;
  }

}
