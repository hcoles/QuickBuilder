package org.pitest.quickbuilder;

/**
 * Builder interface which must be extended by all QuickBuilder interfaces. It
 * should not be implemented by concrete classes, instead the extended interface
 * should be passed to the {@link QB} class which will generate an
 * implementation at runtime.
 * 
 * @param <T> Type to be built.
 */
public interface Builder<T> {

  /**
   * Constructs a T using state within the builder
   * 
   * @return a T
   */
  T build();

  /**
   * Returns the next builder in the sequence if available.
   * 
   * @return Some T or None
   */
  Maybe<Builder<T>> next();
 

}