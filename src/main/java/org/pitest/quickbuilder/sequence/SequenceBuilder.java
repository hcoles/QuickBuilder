package org.pitest.quickbuilder.sequence;

import java.util.List;

import org.pitest.quickbuilder.Builder;

public interface SequenceBuilder<T> extends Builder<T> {

  /**
   * Builds number instances of T.
   * 
   * List will be the requested length unless this would
   * exhaust a sequence within the builder in which case
   * the maximum possible sized list will be returned
   * 
   * @param number number of instances to build
   * @return A List of Ts. List will be of length number or less
   */
  List<T> build(int number);
    
}
