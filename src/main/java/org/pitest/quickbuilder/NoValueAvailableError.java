package org.pitest.quickbuilder;

/**
 * Thrown when a value is requested but none is available.
 *
 */
public final class NoValueAvailableError extends QuickBuilderError {

  private static final long serialVersionUID = 1L;

  public NoValueAvailableError(final String message) {
    super(message);
  }

}
