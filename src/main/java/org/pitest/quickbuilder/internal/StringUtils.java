package org.pitest.quickbuilder.internal;

public class StringUtils {
  
  public static String[] parseCamelCase(String s) {
    return s.split("(?<!^)(?=[A-Z])");
  }
  
  public static String repeat(String s, int times) {
    return new String(new char[times]).replace("\0", s);
  }

}
