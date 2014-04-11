package org.pitest.quickbuilder.internal;

import org.objectweb.asm.Type;

class Setter {

  private final String name;
  private final Type   type;

  Setter(final String name, final Type type) {
    this.name = name;
    this.type = type;
  }

  String name() {
    return this.name;
  }

  String desc() {
    return "(" + this.type.getDescriptor() + ")V";
  }

}
