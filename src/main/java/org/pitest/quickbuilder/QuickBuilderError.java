package org.pitest.quickbuilder;

public final class QuickBuilderError extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public QuickBuilderError(String message, Exception cause) {
    super(message,cause);
  }
  
  public QuickBuilderError(Exception cause) {
    super(cause);
  }
    
  public QuickBuilderError(String message) {
    super(message);
  }
}
