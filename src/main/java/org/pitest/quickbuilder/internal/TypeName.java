package org.pitest.quickbuilder.internal;

class TypeName {
  
  private final String name;
  
  static TypeName fromString(String s) {
    return new TypeName(s);
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
