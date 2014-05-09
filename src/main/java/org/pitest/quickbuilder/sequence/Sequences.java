package org.pitest.quickbuilder.sequence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;

/**
 * Operations on builders to generate sequences of values
 */
public abstract class Sequences {

  /**
   * Builds a list with the maximum possible number of entries.
   * 
   * WARNING. If called on a builder that is composed of only constants then
   * this method will not terminate as the list will be of inifnite size.
   * 
   * The size of the list is limited by the sizes of the shortest sequence
   * within the builder graph.
   * 
   * @param builder Builder from which to generate list
   * @param<T> Type to build
   * @return A List of Ts.
   */
  public static <T> List<T> buildAll(final Builder<T> builder) {
    final List<T> ts = new ArrayList<T>();
    Maybe<Builder<T>> next = Maybe.some(builder);
    while (next.hasSome()) {
      ts.add(next.value().build());
      next = next.value().next();
    }
    return ts;
  }

  /**
   * Builds number instances of T.
   * 
   * List will be the requested length unless this would exhaust a sequence
   * within the builder in which case the maximum possible sized list will be
   * returned
   * 
   * @param builder Builder from which to generate list
   * @param number number of instances to build
   * @param<T> Type to build
   * @return A List of Ts. List will be of length number or less
   */
  public static <T> List<T> build(final Builder<T> builder, final int number) {
    final List<T> ts = new ArrayList<T>();
    Maybe<Builder<T>> next = Maybe.some(builder);
    int count = 0;
    while (next.hasSome() && (count < number)) {
      ts.add(next.value().build());
      next = next.value().next();
      count++;
    }
    return ts;
  }
  
  /**
   * Limits the sequence represented by this builder.
   * 
   * @param builder The builder to limit
   * @param limit The maximum length of the sequence
   * @param<T> Type to build
   * @return A builder sequence of at most limit length
   */
  public static <T> SequenceBuilder<T> limit(final Builder<T> builder, final int limit) {
    return LimitingBuilder.limit(limit, builder);
  }
  
  /**
   * Creates an iterator over the values in the builder
   * 
   * 
   * @param builder The builder to iterator over
   * @param<T> Type to build
   * @return An iterator over the values in the builder
   */
  public static <T> Iterator<T> iterator(Builder<T> builder) {
     return BuilderIterator.iterator(builder);
  }
  
  /**
   * Wraps a builder in the richer SequenceBuilder interface
   * 
   * 
   * @param builder The builder to iterator over
   * @param<T> Type to build
   * @return An iterator over the values in the builder
   */
  public static <T> SequenceBuilder<T> decorate(Builder<T> builder) {
     return new SequenceWrapper<T>(builder);
  }  
}
