package org.pitest.quickbuilder.common;

import static org.junit.Assert.*;

import org.junit.Test;
import org.pitest.quickbuilder.common.AsString;

public class AsStringTest {

  @Test
  public void shouldConvertObjectsToString() {
    assertEquals("11",new AsString<Integer>().convert(11));
  }

}
