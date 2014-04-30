package org.pitest.quickbuilder.sequence;

import java.util.ArrayList;
import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;
import org.pitest.quickbuilder.NoValueAvailableError;
import org.pitest.quickbuilder.internal.Iterables;

/**
 * Generates a builder that iterates through the supplied values.
 * 
 * @param <T> Type to build
 */
public final class ElementSequence<T> implements Builder<T> {

  private final List<T> ts;
  private final int     position;

  private ElementSequence(final List<T> ts, final int position) {
    this.ts = new ArrayList<T>(ts);
    this.position = position;
  }

  public static <T> ElementSequence<T> from(final Iterable<T> ts) {
    return new ElementSequence<T>(Iterables.asList(ts), 0);
  }

  @Override
  public T build() {
    if (this.position >= this.ts.size()) {
      throw new NoValueAvailableError(
          "Requested a value from sequence, but no values available");
    }
    return this.ts.get(this.position);
  }

  private boolean hasNext() {
    return (this.position + 1) < this.ts.size();
  }

  @Override
  public Maybe<Builder<T>> next() {
    if (hasNext()) {
      return Maybe.<Builder<T>> some(new ElementSequence<T>(this.ts,
          this.position + 1));
    }
    return Maybe.none();
  }

}