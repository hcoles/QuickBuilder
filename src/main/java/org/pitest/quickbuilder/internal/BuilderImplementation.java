package org.pitest.quickbuilder.internal;

import java.util.ArrayList;
import java.util.List;

import org.pitest.quickbuilder.Builder;
import org.pitest.quickbuilder.MutableBuilder;
import org.pitest.quickbuilder.sequence.ConstantBuilder;

public class BuilderImplementation {

  public static Builder<?> wrapProperty(final Object property) {
    if (property instanceof Builder) {
      return new ConstantBuilder<Object>(property);
    } else {
      return (Builder<?>) property;
    }
  }

  public static Builder<?> copyBuilder(final Builder<?> b) {
    if (b == null) {
      return null;
    }

    if (b instanceof MutableBuilder) {
      return ((MutableBuilder<?>) b).but();
    }

    return b;
  }

  public static List<?> buildSequence(final Builder<?> b, final int size) {
    final List<Object> l = new ArrayList<Object>(size);
    for (int i = 0; i != size; i++) {
      l.add(b.build());
    }
    return l;
  }

}
