package org.pitest.quickbuilder;

import java.util.List;

/**
 * Builder with support for directly generating sequences of values.
 * 
 * @param <T> Type to be built.
 */
public interface SequenceBuilder<T> extends Builder<T> {

/**
 * Construct a list of values
 * @param number Number of values to construct
 * @return A List of values
 */
  List<T> build(int number);
  
}
