package org.pitest.quickbuilder.common;

import java.util.Iterator;
import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;
import org.pitest.quickbuilder.SequenceBuilder;

public class LimitingBuilder<T> implements SequenceBuilder<T> {

  private final Builder<T> child;
  private final int        remaining;

  private LimitingBuilder(int times, Builder<T> child) {
    remaining = times - 1;
    this.child = child;
  }

  public static <T> SequenceBuilder<T> limit(int times, Builder<T> child) {
    if ( times <= 0 ) {
      return new NonBuilder<T>();
    }
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
  
  @Override
  public SequenceBuilder<T> limit(int limit) {
    return LimitingBuilder.limit(limit, this);
  }

  @Override
  public List<T> build(int number) {
    return Sequences.build(this, number);
  }

  @Override
  public List<T> buildAll() {
    return Sequences.buildAll(this);
  }
  
  @Override
  public Iterator<T> iterator() {
    return Sequences.iterator(this);
  }  
}
