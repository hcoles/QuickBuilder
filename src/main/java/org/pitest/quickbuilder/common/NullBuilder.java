package org.pitest.quickbuilder.common;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;

/**
 * Always builds null
 * 
 * @param <T> Type to never build
 */
public final class NullBuilder<T> implements Builder<T> {

  @Override
  public T build() {
    return null;
  }

  @Override
  public Maybe<Builder<T>> next() {
    return Maybe.<Builder<T>>some(this);
  }

}
