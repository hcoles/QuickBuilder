package org.pitest.quickbuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;

/**
 * Holder for one or no values - the typesafe null pattern. Finally included in
 * Java 8 as optional, but implemented here for use with early jdks.
 * 
 * @param <T>
 *          Type to hold
 */
public abstract class Maybe<T> implements Iterable<T>, Serializable {

  private static final long serialVersionUID = 1L;

  @SuppressWarnings({ "rawtypes" })
  private final static None NONE             = new None();

  private Maybe() {
  }

  public abstract T value();

  public abstract T getOrElse(T defaultValue);

  public abstract boolean hasSome();

  @SuppressWarnings("unchecked")
  public static <T> Maybe<T> some(final T value) {
    if (value == null) {
      return NONE;
    } else {
      return new Some<T>(value);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> None<T> none() {
    return NONE;
  }

  public boolean hasNone() {
    return !hasSome();
  }

  public final static class None<T> extends Maybe<T> {

    private static final long serialVersionUID = 1L;

    private None() {

    }

    @Override
    public Iterator<T> iterator() {
      return Collections.<T> emptySet().iterator();
    }

    @Override
    public T value() {
      throw new UnsupportedOperationException(
          "Tried to retrieve value but had None.");
    }

    @Override
    public T getOrElse(final T defaultValue) {
      return defaultValue;
    }

    @Override
    public boolean hasSome() {
      return false;
    }

  }

  public final static class Some<T> extends Maybe<T> {

    private static final long serialVersionUID = 1L;

    private final T           value;

    private Some(final T value) {
      this.value = value;
    }

    @Override
    public T value() {
      return this.value;
    }

    @Override
    public Iterator<T> iterator() {
      return Collections.singleton(this.value).iterator();

    }

    @Override
    public T getOrElse(final T defaultValue) {
      return this.value;
    }

    @Override
    public boolean hasSome() {
      return true;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = (prime * result)
          + ((this.value == null) ? 0 : this.value.hashCode());
      return result;
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      @SuppressWarnings("rawtypes")
      final Some other = (Some) obj;
      if (this.value == null) {
        if (other.value != null) {
          return false;
        }
      } else if (!this.value.equals(other.value)) {
        return false;
      }
      return true;
    }

    @Override
    public String toString() {
      return "Some(" + this.value + ")";
    }

  }

}