package org.pitest.quickbuilder.sequence;

import java.util.ArrayList;
import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.Maybe;

/**
 * Operations on builders to generate sequences of values
 *
 */
public class Sequences {

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
  
  public static <T> Builder<T> limit(final Builder<T> builder, final int times) {
    return LimitingBuilder.limit(times, builder);
  }

}
