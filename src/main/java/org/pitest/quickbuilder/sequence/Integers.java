package org.pitest.quickbuilder.sequence;

import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;
import org.pitest.quickbuilder.QuickBuilderError;

public class Integers implements SequenceBuilder<Integer> {
  
  private final int current;
  
  public static Integers integersFrom(int start) {
    return new Integers(start);
  }
  
  private Integers(int current) {
    this.current = current;
  }

  @Override
  public Integer build() {
    return current;
  }

  @Override
  public Maybe<Builder<Integer>> next() {
    return Maybe.<Builder<Integer>>some(new Integers(current + 1));
  }

  @Override
  public List<Integer> build(int number) {
    return Sequences.build(this, number);
  }

  @Override
  public List<Integer> buildAll() {
    throw new QuickBuilderError("buildAll called on an infinite sequence of Integers. You did not want to do this.");
  }

  @Override
  public SequenceBuilder<Integer> limit(int limit) {
    return Sequences.limit(this, limit);
  }

}
