package org.pitest.quickbuilder;

import org.pitest.quickbuilder.internal.TypeScanner;

public abstract class QB {

  /**
   * Create a Builder implementation for the supplied interface.
   * 
   * The built type must provide an accessible no-args constructor and a setter
   * method for each property defined on the builder interface.
   * 
   * The first call to this method for any given interface will be relatively 
   * expensive as it will trigger the generation of a class, subsequent calls
   * will be cheap as the class will be loaded into the jvm like any other. 
   * 
   * @param builder Interface to implement
   * @return A builder instance
   */
  @SuppressWarnings("unchecked")
  public static <T, B extends Builder<T>> B builder(final Class<B> builder) {
    return builder(builder, null);
  }

  /**
   * Create a Builder implementation for the supplied interface, using the 
   * supplied seed instance to instantiate the class.
   * 
   * The first call to this method for any given interface will be relatively 
   * expensive as it will trigger the generation of a class, subsequent calls
   * will be cheap as the class will be loaded into the jvm like any other. 
   * 
   * @param builder Interface to implement
   * @param seed Function object that constructs instance of built type
   * @return A builder instance
   */
  @SuppressWarnings("unchecked")
  public static <T, B extends Builder<T>> B builder(final Class<B> builder,
      final Generator<T, B> seed) {
    final TypeScanner<T, B> ts = new TypeScanner<T, B>(builder, seed);
    return ts.builder();
  }

}
