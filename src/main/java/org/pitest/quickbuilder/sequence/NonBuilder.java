package org.pitest.quickbuilder.sequence;

import java.util.Collections;
import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;
import org.pitest.quickbuilder.NoValueAvailableError;

/**
 * Builds nothing
 *
 * @param <T> Type to never build
 */
public class NonBuilder<T> implements SequenceBuilder<T> {

  @Override
  public T build() {
    throw new NoValueAvailableError("No value available");
  }

  @Override
  public Maybe<Builder<T>> next() {
    return Maybe.none();
  }

  @Override
  public List<T> build(int number) {
    return Collections.emptyList();
  }

  @Override
  public List<T> buildAll() {
    return Collections.emptyList();
  }

  @Override
  public SequenceBuilder<T> limit(int limit) {
    return this;
  }
  
}
