package org.pitest.quickbuilder.sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;

public class ComposedBuilder<T> implements SequenceBuilder<T> {
  
  private final List<Builder<T>> children;
  
  private ComposedBuilder(List<Builder<T>> children) {
    this.children = children;
  }

  @SafeVarargs
  public static <T> SequenceBuilder<T> compose(Builder<T> ... children ) {
    return make(Arrays.asList(children));
  } 
  
  private static <T> SequenceBuilder<T> make(List<Builder<T>> children) {
    if (children.isEmpty() ) {
      return new NonBuilder<T>();
    }
    return new ComposedBuilder<T>(children);
  }
  
  @Override
  public T build() {
    return children.get(0).build();
  }


  @Override
  public Maybe<Builder<T>> next() {
    Maybe<Builder<T>> nextInSequence = children.get(0).next();
    if (nextInSequence.hasSome()) {
      List<Builder<T>> next = new ArrayList<Builder<T>>(children);
      next.set(0, nextInSequence.value());
      return Maybe.<Builder<T>> some(make(next));
    }

    LinkedList<Builder<T>> next = new LinkedList<Builder<T>>(children);
    next.removeFirst();
    if (next.isEmpty()) {
      return Maybe.none();
    }
    return Maybe.<Builder<T>> some(make(next));

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
    return Sequences.limit(this,limit);
  }

  @Override
  public Iterator<T> iterator() {
    return Sequences.iterator(this);
  }

}
