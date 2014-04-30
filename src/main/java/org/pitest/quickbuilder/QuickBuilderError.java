package org.pitest.quickbuilder;

public class QuickBuilderError extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public QuickBuilderError(final String message, final Exception cause) {
    super(message, cause);
  }

  public QuickBuilderError(final Exception cause) {
    super(cause);
  }

  public QuickBuilderError(final String message) {
    super(message);
  }
}
