package org.pitest.quickbuilder.internal;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class Property {

  // name and type alone define property
  private final String name;
  private final Type   type;
  
  private final String prefix;
  private final Type declaredType;
  private Setter setter;

  Property(final String name, String prefix, final Type type, final Type declaredType, final Setter s) {
    this.name = name;
    this.prefix = prefix;
    this.type = type;
    this.declaredType = declaredType;
    this.setter = s;
  }

  String name() {
    return this.name;
  }

  String withMethodName() {
    return prefix + this.name;
  }

  String type() {
    return this.type.getDescriptor();
  }
  
  public String typeName() {
    return this.type.getInternalName();
  }

  Setter setter() {
    return this.setter;
  }

  public int loadIns() {
    return this.type.getOpcode(Opcodes.ILOAD);
  }

  public int returnOp() {
    return this.type.getOpcode(Opcodes.IRETURN);
  }

  public boolean isHasSetter() {
    return this.setter != null;
  }
  
  String declaredType() {
    return this.declaredType.getDescriptor();
  }
  
  String declaredTypeName() {
    return this.declaredType.getInternalName();
  }
  
  boolean isBuilder() {
    return !declaredType().equals(type());
  }
  
  public void disableSetter() {
    this.setter = null;
  }
  
  public int getSort() {
    return this.type.getSort();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Property other = (Property) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }






  
  

}
