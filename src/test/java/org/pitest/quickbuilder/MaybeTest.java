package org.pitest.quickbuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

public class MaybeTest {

  private static final String FOO = "foo";

  @Test
  public void someShouldReturnNoneWhenPassedNull() {
    assertEquals(Maybe.none(), Maybe.some(null));
  }

  @Test
  public void someShouldNotReturnNoneWhenPassedAValue() {
    assertFalse(Maybe.some(FOO).equals(Maybe.none()));
  }

  @Test
  public void shouldEqualItself() {
    assertEquals(Maybe.none(), Maybe.none());
  }

  @Test
  public void shouldBeEqualsWhenConstructedWithSameValue() {
    assertEquals(Maybe.some(FOO), Maybe.some(FOO));
  }

  @Test
  public void shouldNotBeEqualWhenConstructedWithDifferentValuesAreNotEqual() {
    assertFalse(Maybe.some(FOO).equals(Maybe.some("bar")));
  }

  @Test
  public void noneShouldNotHaveSome() {
    assertFalse(Maybe.none().hasSome());
  }

  @Test
  public void someShouldHaveSome() {
    assertTrue(Maybe.some(FOO).hasSome());
  }

  @Test
  public void someShouldNotHaveNone() {
    assertFalse(Maybe.some(FOO).hasNone());
  }

  @Test
  public void noneShouldHaveNone() {
    assertTrue(Maybe.none().hasNone());
  }

  @Test
  public void shouldReturnUnderlyingValueWhenWeHaveSome() {
    assertEquals(FOO, Maybe.some(FOO).value());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shouldThrowErrorIfTryToRetrieveValueWhenWeHaveNone() {
    Maybe.none().value();
  }

  @Test
  public void shouldIterateOverNonValuesWhenNoneArePresent() {
    assertFalse(Maybe.none().iterator().hasNext());
  }

  @Test
  public void shouldIterateOverExactlyOneValueWhenOneIsPresent() {
    final Maybe<String> testee = Maybe.some(FOO);
    final Iterator<String> it = testee.iterator();
    assertEquals(FOO, it.next());
    assertFalse(it.hasNext());
  }

  @Test
  public void getOrElseShouldReturnElseConditionWhenNonePresent() {
    assertEquals("else", Maybe.none().getOrElse("else"));
  }

  @Test
  public void getOrElseShouldReturnValueWhenSomePresent() {
    assertEquals(FOO, Maybe.some(FOO).getOrElse("else"));
  }

}
