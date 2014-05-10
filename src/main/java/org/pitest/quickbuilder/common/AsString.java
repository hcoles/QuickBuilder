package org.pitest.quickbuilder.common;

import org.pitest.quickbuilder.Conversion;

public class AsString<A> implements Conversion<A,String> {

  @Override
  public String convert(A a) {
    return a.toString();
  }

}
