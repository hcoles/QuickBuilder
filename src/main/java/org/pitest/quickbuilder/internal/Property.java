package org.pitest.quickbuilder.internal;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class Property {

  // name and type alone define property
  private final String name;
  private final Type   type;

  private final String prefix;
  private final Type   declaredType;
  private final Type   bridgeReturnType;
  private final String owner;

  private Setter       setter;

  Property(final String name, final String owner, final String prefix,
      final Type type, final Type declaredType, final Type bridgeReturnType,
      final Setter s) {
    this.name = name;
    this.prefix = prefix;
    this.type = type;
    this.declaredType = declaredType;
    this.setter = s;
    this.owner = owner;
    this.bridgeReturnType = bridgeReturnType;
  }

  String name() {
    return this.name;
  }

  String withMethodName() {
    return this.prefix + this.name;
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

  boolean isBuilder() {
    return !declaredType().equals(type());
  }

  public void disableSetter() {
    this.setter = null;
  }

  public int getSort() {
    return this.type.getSort();
  }

  public String owner() {
    return this.owner;
  }

  public Type bridgeReturnType() {
    return this.bridgeReturnType;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = (prime * result)
        + ((this.name == null) ? 0 : this.name.hashCode());
    result = (prime * result)
        + ((this.type == null) ? 0 : this.type.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Property other = (Property) obj;
    if (this.name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!this.name.equals(other.name)) {
      return false;
    }
    if (this.type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!this.type.equals(other.type)) {
      return false;
    }
    return true;
  }

  public boolean needsBridge() {
    return this.bridgeReturnType != null;
  }

}
