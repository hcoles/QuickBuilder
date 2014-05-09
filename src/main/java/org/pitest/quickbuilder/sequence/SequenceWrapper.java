package org.pitest.quickbuilder.sequence;

import java.util.Iterator;
import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;

class SequenceWrapper<T> implements SequenceBuilder<T>{

  private final Builder<T> child;
  
  SequenceWrapper(Builder<T> child) {
    this.child = child;
  }

  @Override
  public T build() {
    return child.build();
  }

  @Override
  public Maybe<Builder<T>> next() {
    return child.next();
  }

  @Override
  public Iterator<T> iterator() {
    return Sequences.iterator(this);
  }

  @Override
  public List<T> build(int number) {
    return Sequences.build(this,number);
  }

  @Override
  public List<T> buildAll() {
    return Sequences.buildAll(this);
  }

  @Override
  public SequenceBuilder<T> limit(int limit) {
    return Sequences.limit(this,limit);
  }
  
}
