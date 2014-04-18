package org.pitest.quickbuilder;

/**
 * Function interface that provides a "seed" for building objects.
 * Implementations of this interface can be used to construct and
 * set values on classes that QuickBuilder cannot handle automatically.
 *
 * @param <T> The built type
 * @param <B> The builder type that produces the built type
 */
public interface Generator<B extends Builder<T>, T> {

  T generate(B builder);

}
