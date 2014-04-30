package org.pitest.quickbuilder.sequence;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;

/**
 * Builds a constant value
 *
 * @param <T> Type to build
 */
public class ConstantBuilder<T> implements Builder<T> {

  private final T value;

  public ConstantBuilder(final T value) {
    this.value = value;
  }

  @Override
  public T build() {
    return this.value;
  }

  @Override
  public Maybe<Builder<T>> next() {
    return Maybe.<Builder<T>> some(this);
  }
}
