package org.pitest.quickbuilder.sequence;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;

public class LimitingBuilder<T> implements Builder<T> {

  private final Builder<T> child;
  private final int        remaining;

  private LimitingBuilder(int times, Builder<T> child) {
    remaining = times - 1;
    this.child = replaceIfZero(times,child);
  }

  public static <T> LimitingBuilder<T> limit(int times, Builder<T> child) {
    return new LimitingBuilder<T>(times, child);
  }

  public T build() {
    return child.build();
  }

  @Override
  public Maybe<Builder<T>> next() {
    if (remaining > 0) {
      return Maybe.<Builder<T>> some(new LimitingBuilder<T>(
          remaining, child.next().getOrElse(new NonBuilder<T>())));
    }
    return Maybe.none();
  }
  
  private static <T> Builder<T> replaceIfZero(int times, Builder<T> child) {
    if ( times <= 0 ) {
      return new NonBuilder<T>();
    }
    return child;
  }

}
