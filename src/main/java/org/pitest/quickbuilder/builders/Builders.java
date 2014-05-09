package org.pitest.quickbuilder.builders;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.sequence.AsString;
import org.pitest.quickbuilder.sequence.ConstantBuilder;
import org.pitest.quickbuilder.sequence.ConvertingBuilder;
import org.pitest.quickbuilder.sequence.Integers;
import org.pitest.quickbuilder.sequence.NullBuilder;
import org.pitest.quickbuilder.sequence.RepeatedBuilder;
import org.pitest.quickbuilder.sequence.SequenceBuilder;
import org.pitest.quickbuilder.sequence.Sequences;

public class Builders {

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
  public static <T> SequenceBuilder<T> once(Builder<T> builder) {
    return RepeatedBuilder.once(builder);
  }
  
  
  /**
   * Creates a sequence that repeats the given value only once.
   * 
   * @param builder The value
   * @param<T> Type to build 
   * @return A builder sequence of exactly 1 length
   */
  public static <T> SequenceBuilder<T> once(T value) {
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
  public static <T> SequenceBuilder<T> constant(T value) {
    return Sequences.decorate(ConstantBuilder.constant(value));
  }
  
  /**
   * Creates a builder that always returns the
   * same instance of the produced value
   * 
   * @param builder The builder to supply the value
   * @param<T> Type to build
   * @return A builder that always returns the same value
   */
  public static <T> SequenceBuilder<T> theSame(Builder<T> builder) {
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
  public static <T> SequenceBuilder<T> nullValue() {
    return Sequences.decorate(new NullBuilder<T>());
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
  
  /**
   * Converts a builder to return a string representation of it type
   * 
   * 
   * @param builder The builder to supply the value
   * @param<T> Type to build
   * @return A builder that returns string representations of the built value
   */
  public static <T> SequenceBuilder<String> asString(Builder<T> builder) {
    return new ConvertingBuilder<T,String>(builder, new AsString<T>());
  }
   
  
}
