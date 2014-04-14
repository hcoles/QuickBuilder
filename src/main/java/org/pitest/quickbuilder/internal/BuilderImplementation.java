package org.pitest.quickbuilder.internal;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.MutableBuilder;

public class BuilderImplementation {
  
  public static Builder<?> wrapProperty(Object property) {
    if ( property instanceof Builder ) {
      return new StoredValueBuilder<Object>(property);
    } else {
      return (Builder<?>) property;
    }
  }

  public static Builder<?> copyBuilder(Builder<?> b) {
    if ( b == null ) {
      return null;
    }
    
    if ( b instanceof MutableBuilder) {
      return ((MutableBuilder<?>)b).but();
    }
    
    return b;
  }
  
}
