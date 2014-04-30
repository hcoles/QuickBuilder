package org.pitest.quickbuilder.sequence;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;
import org.pitest.quickbuilder.NoValueAvailableError;

public class NonBuilder<T> implements Builder<T> {

  Builder<T> b;
  Builder<T> c;

  @Override
  public T build() {
    throw new NoValueAvailableError("No value available");
  }

  @Override
  public Maybe<Builder<T>> next() {
    return Maybe.none();
  }

}
