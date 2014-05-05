package org.pitest.quickbuilder.internal;

import org.pitest.quickbuilder.Builder;

public class BuilderImplementation {

  public static Builder<?> copyBuilder(final Builder<?> b) {
    if (b == null) {
      return null;
    }
    return b;
  }
}
