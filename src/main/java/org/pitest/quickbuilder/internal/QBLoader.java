package org.pitest.quickbuilder.internal;

public class QBLoader extends ClassLoader {

  public QBLoader(final ClassLoader parent) {
    super(parent);
  }

  @Override
  public Class<?> findClass(final String name) {
    return findLoadedClass(name);
  }

  public Class<?> createClass(final byte[] b, final String name) {
    return super.defineClass(name, b, 0, b.length);
  }

}
