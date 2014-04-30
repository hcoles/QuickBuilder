package org.pitest.quickbuilder;

public class Transistion <T,U> {

  private final T state;
  private final Maybe<U> value;
  
  Transistion(T state,  Maybe<U> value) {
    this.state = state;
    this.value = value;
  }
  
  public static <T,U> Transistion <T,U> transistion(T state,  Maybe<U> value) {
    return new Transistion <T,U>(state, value);
  }
  
  
  T newState() {
    return state;
  }
  
  Maybe<U> value() {
    return value;
  }
  
}
