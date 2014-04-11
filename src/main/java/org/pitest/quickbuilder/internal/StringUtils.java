package org.pitest.quickbuilder.internal;

public class StringUtils {
  
  public static String[] parseCamelCase(String s) {
    return s.split("(?<!^)(?=[A-Z])");
  }

}
