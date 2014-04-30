package org.pitest.quickbuilder.internal;

class TypeName {

  private final String name;

  static TypeName fromString(final String s) {
    return new TypeName(s);
  }

  static TypeName fromClass(final Class<?> c) {
    return new TypeName(c.getName().replace(".", "/"));
  }

  TypeName(final String name) {
    this.name = name;
  }

  String name() {
    return this.name;
  }

  String type() {
    return "L" + this.name + ";";
  }

}
