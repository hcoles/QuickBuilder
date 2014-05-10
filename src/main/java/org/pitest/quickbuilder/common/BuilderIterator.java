package org.pitest.quickbuilder.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.NoValueAvailableError;

public class BuilderIterator<T> implements Iterator<T> {

  private static final Builder<?> FIN = new NonBuilder<Object>();

  private Builder<T>              builder;

  BuilderIterator(final Builder<T> builder) {
    this.builder = builder;
  }

  public static <T> Iterator<T> iterator(Builder<T> builder) {
    return new BuilderIterator<T>(builder);
  }
  
  @Override
  public boolean hasNext() {
    return this.builder.next().hasSome();
  }

  @SuppressWarnings("unchecked")
  @Override
  public T next() {
    try {
      final T value = this.builder.build();
      this.builder = this.builder.next().getOrElse((Builder<T>) FIN);
      return value;
    } catch (final NoValueAvailableError ex) {
      throw new NoSuchElementException();
    }
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }

}
