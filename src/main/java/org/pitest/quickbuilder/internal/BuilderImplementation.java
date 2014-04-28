package org.pitest.quickbuilder.internal;

import java.util.ArrayList;
import java.util.List;

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
  
  public static List<?> buildSequence(Builder<?> b, int size) {
    List<Object> l = new ArrayList<Object>(size);
    for ( int i = 0; i != size; i++ ) {
      l.add(b.build());
    }
    return l;
  }
  
  public static int findLimit(int initial, Builder<?> b) {
    if ( b != null ) {
      int val = b.valueLimit();
      if (initial == -1 || (val != -1 && val < initial) ) {
        return val;
      }
    }
    return initial;
  }
  
}
