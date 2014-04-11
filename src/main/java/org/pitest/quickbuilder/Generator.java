package org.pitest.quickbuilder;

public interface Generator<T, B extends Builder<T>> {

  T generate(B builder);

}
