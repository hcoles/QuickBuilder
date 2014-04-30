package org.pitest.quickbuilder;

public class Transistion<T, U> {

  private final T        state;
  private final Maybe<U> value;

  Transistion(final T state, final Maybe<U> value) {
    this.state = state;
    this.value = value;
  }

  public static <T, U> Transistion<T, U> transistion(final T state,
      final Maybe<U> value) {
    return new Transistion<T, U>(state, value);
  }

  T newState() {
    return this.state;
  }

  Maybe<U> value() {
    return this.value;
  }

}
