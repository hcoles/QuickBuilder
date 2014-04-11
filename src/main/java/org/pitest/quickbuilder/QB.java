package org.pitest.quickbuilder;

import org.pitest.quickbuilder.internal.TypeScanner;

public abstract class QB {

  @SuppressWarnings("unchecked")
  public static <T, B extends Builder<T>> B builder(final Class<B> builder) {
    return builder(builder, null);
  }

  @SuppressWarnings("unchecked")
  public static <T, B extends Builder<T>> B builder(final Class<B> builder,
      final Generator<T, B> g) {
    final TypeScanner<T, B> ts = new TypeScanner<T, B>(builder, g);
    return ts.builder();
  }

}
