package org.pitest.quickbuilder.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.internal.Iterables;

/**
 * Generates a builder that iterates through the supplied values.
 *
 * @param <T> Type to build
 */
public final class ElementSequence<T> implements Builder<T> {
  
  private final List<T> ts;
  private final ListIterator<T> current;

  private ElementSequence(List<T> ts) {
    this.ts = new ArrayList<T>(ts);
    this.current = ts.listIterator();
  }
  
  public static <T> ElementSequence<T> from(Iterable<T> ts) {
    return new ElementSequence<T>(Iterables.asList(ts));
  }
  
  @Override
  public T build() {
    return current.next();
  }

  @Override
  public ElementSequence<T> but() {
    return new ElementSequence<T>(ts.subList(current.nextIndex(), ts.size()));
  }

}
