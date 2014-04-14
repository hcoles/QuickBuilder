package org.pitest.quickbuilder.internal;

import org.pitest.quickbuilder.Builder;

public class StoredValueBuilder<T> implements Builder<T> {
  
  private final T value;
  
  public StoredValueBuilder(T value) {
    this.value = value;
  }

  @Override
  public T build() {
    return value;
  }

}
