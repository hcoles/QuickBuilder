package org.pitest.quickbuilder.internal;

class TypeName {
  
  private final String name;
  
  static TypeName fromString(String s) {
    return new TypeName(s);
  }
  
  static TypeName fromClass(Class<?> c) {
    return new TypeName(c.getName().replace(".", "/"));
  }
  
  TypeName(String name) {
    this.name = name;
  }
  
  String name() {
    return name;
  }
  
  String type() {
    return "L" + name + ";";
  }

}
