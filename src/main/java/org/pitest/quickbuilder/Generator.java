package org.pitest.quickbuilder;

/**
 * Function interface that provides a "seed" for building objects.
 * Implementations of this interface can be used to construct and set values on
 * classes that QuickBuilder cannot handle automatically.
 * 
 * @param <T> The built type
 * @param <B> The builder type that produces the built type
 */
public interface Generator<B extends Builder<T>, T> {

  /**
   * Creates an instance of T from B
   * 
   * @param builder Builder from which to source values
   * @return An instance of T
   */
  T generate(B builder);

}
