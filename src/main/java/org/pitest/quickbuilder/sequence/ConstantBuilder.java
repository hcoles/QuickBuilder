package org.pitest.quickbuilder.sequence;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;

public class ConstantBuilder<T> implements Builder<T> {
  
  private final T value;
  
  public ConstantBuilder(T value) {
    this.value = value;
  }

  @Override
  public T build() {
    return value;
  }

  @Override
  public Maybe<Builder<T>> next() {
    return Maybe.<Builder<T>>some(this);
  }
}
