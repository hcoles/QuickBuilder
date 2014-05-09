package org.pitest.quickbuilder.sequence;

import org.pitest.quickbuilder.Conversion;

public class AsString<A> implements Conversion<A,String> {

  @Override
  public String convert(A a) {
    return a.toString();
  }

}
