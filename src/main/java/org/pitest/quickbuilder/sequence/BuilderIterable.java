package org.pitest.quickbuilder.sequence;

import java.util.Iterator;

import org.pitest.quickbuilder.Builder;

public class BuilderIterable<T> implements Iterable<T> {
  
  private final Builder<T> builder;
  
  public BuilderIterable(Builder<T> builder) {
    this.builder = builder;
  }

  @Override
  public Iterator<T> iterator() {
    return new BuilderIterator<T>(builder);
  }

}
