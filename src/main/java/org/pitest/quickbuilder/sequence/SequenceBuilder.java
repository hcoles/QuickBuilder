package org.pitest.quickbuilder.sequence;

import java.util.List;

import org.pitest.quickbuilder.Builder;

/**
 * A Builder that provides convenience methods for creating sequences of values
 * 
 * @param <T> The type to build
 */
public interface SequenceBuilder<T> extends Builder<T>, Iterable<T> {

  /**
   * Builds number instances of T.
   * 
   * List will be the requested length unless this would exhaust a sequence
   * within the builder in which case the maximum possible sized list will be
   * returned
   * 
   * @param number number of instances to build
   * @return A List of Ts. List will be of length number or less
   */
  List<T> build(int number);

  /**
   * Builds a list with the maximum possible number of entries.
   * 
   * WARNING. If called on a builder that is composed of only constants then
   * this method will not terminate as the list will be of inifnite size.
   * 
   * The size of the list is limited by the sizes of the shortest sequence
   * within the builder graph.
   * 
   * 
   * @return A List of Ts.
   */
  List<T> buildAll();
  
  /**
   * Limits the sequence represented by this builder.
   * 
   * @param limit The maximum length of the sequence
   * @return A builder sequence of at most limit length
   */
  SequenceBuilder<T> limit(int limit);
}
