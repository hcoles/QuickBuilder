package org.pitest.quickbuilder.sequence;

import static org.junit.Assert.*;

import org.junit.Test;

public class AsStringTest {

  @Test
  public void shouldConvertObjectsToString() {
    assertEquals("11",new AsString<Integer>().convert(11));
  }

}
