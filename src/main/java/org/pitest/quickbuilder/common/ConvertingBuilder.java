package org.pitest.quickbuilder.common;

import java.util.Iterator;
import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Conversion;
import org.pitest.quickbuilder.Maybe;
import org.pitest.quickbuilder.SequenceBuilder;

public class ConvertingBuilder<A,T> implements SequenceBuilder<T> {
  
  private final Builder<A> child;
  private final Conversion<A,T> converter;
  
  public ConvertingBuilder(Builder<A> child, Conversion<A,T> converter) {
    this.child = child;
    this.converter = converter;
  }

  @Override
  public T build() {
    return converter.convert(child.build());
  }

  @Override
  public Maybe<Builder<T>> next() {
    if (child.next().hasNone()) {
      return Maybe.none();
    }
    return Maybe.<Builder<T>>some(new ConvertingBuilder<A,T>(child.next().value(),converter));
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
  public SequenceBuilder<T> limit(int limit) {
    return Sequences.limit(this, limit);
  }
  
  @Override
  public Iterator<T> iterator() {
    return Sequences.iterator(this);
  }  

}
