package org.pitest.quickbuilder;

public interface MutableBuilder<T> extends Builder<T> {
  MutableBuilder<T> but();
}
