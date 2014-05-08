package org.pitest.quickbuilder.sequence;

import java.util.ArrayList;
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
   * Creates a sequence that repeats the given builder.
   * 
   * If the supplied builder is a sequence, its first value will be
   * repeated.
   * 
   * @param builder The builder to repeat
   * @param times The number of times to repeat the builder
   * @param<T> Type to build    
   * @return A builder sequence of exactly times length
   */
  public static <T> SequenceBuilder<T> repeat(Builder<T> builder, int times) {
    return RepeatedBuilder.repeat(times, builder);
  }

  
  /**
   * Creates a sequence that repeats the given value.
   * 
   * @param value The value to repeat
   * @param times The number of times to repeat the builder
   * @param<T> Type to build    
   * @return A builder sequence of exactly times length
   */
  public static <T> SequenceBuilder<T> repeat(T value, int times) {
    return RepeatedBuilder.repeat(times, constant(value));
  }
  
  
  /**
   * Creates a sequence that repeats the given builder once.
   * 
   * @param builder The builder to repeat
   * @param<T> Type to build 
   * @return A builder sequence of exactly 1 length
   */
  public static <T> RepeatedBuilder<T> once(Builder<T> builder) {
    return RepeatedBuilder.once(builder);
  }
  
  
  /**
   * Creates a sequence that repeats the given value only once.
   * 
   * @param builder The value
   * @param<T> Type to build 
   * @return A builder sequence of exactly 1 length
   */
  public static <T> RepeatedBuilder<T> once(T value) {
    return once(constant(value));
  }
  
  /**
   * Creates a builder that always returns the
   * given constant value
   * 
   * @param value The value to always return
   * @param<T> Type to build
   * @return A builder that always returns the same value
   */
  public static <T> Builder<T> constant(T value) {
    return ConstantBuilder.constant(value);
  }
  
  /**
   * Creates a builder that always returns the
   * same instance of the produced value
   * 
   * @param builder The builder to supply the value
   * @param<T> Type to build
   * @return A builder that always returns the same value
   */
  public static <T> Builder<T> theSame(Builder<T> builder) {
    return constant(builder.build());
  }
  
  /**
   * Creates a builder that always returns the
   * same instance of the produced value
   * 
   * @param builder The builder to supply the value
   * @param<T> Type to build
   * @return A builder that always returns the same value
   */
  public static <T> Builder<T> nullValue() {
    return new NullBuilder<T>();
  }
  
  /**
   * Creates a builder that returns an infinite sequence of Integers starting
   * at the given value, incrementing by one each time.
   * 
   * When Integer.MAX_VALUE is reached the sequence will loop to Integer.MIN_VALUE
   * 
   * @param start First integer to build
   * @return A builder that generates and infinite sequence of integers
   */
  public static SequenceBuilder<Integer> integersFrom(int start) {
    return Integers.integersFrom(start);
  }
  
}
