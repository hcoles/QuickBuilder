package org.pitest.quickbuilder.internal;

public class StringUtils {

  public static String[] parseCamelCase(final String s) {
    return s.split("(?<!^)(?=[A-Z])");
  }

  public static String repeat(final String s, final int times) {
    return new String(new char[times]).replace("\0", s);
  }

}
