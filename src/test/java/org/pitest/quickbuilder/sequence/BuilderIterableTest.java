package org.pitest.quickbuilder.sequence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.pitest.quickbuilder.sequence.Sequences.asString;
import static org.pitest.quickbuilder.sequence.Sequences.integersFrom;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class BuilderIterableTest {

  @Test
  public void shouldIterateThroughValues() {
    BuilderIterable<String> testee = new BuilderIterable<String>(
        asString(integersFrom(1)));
    Iterator<String> it = testee.iterator();
    assertEquals("1", it.next());
    assertEquals("2", it.next());
    assertEquals("3", it.next());
  }

  @Test
  public void shouldStopIteratingWhenSequenceExhausted() {
    BuilderIterable<String> testee = new BuilderIterable<String>(
        asString(integersFrom(1).limit(2)));
    Iterator<String> it = testee.iterator();
    assertEquals(true, it.hasNext());
    it.next();
    it.next();
    assertEquals(false, it.hasNext());
  }

  @Test
  public void shouldThrowNoSuchElementExceptionWhenNextCalledOnExhaustedSequence() {
    BuilderIterable<String> testee = new BuilderIterable<String>(
        asString(integersFrom(1).limit(2)));
    Iterator<String> it = testee.iterator();
    it.next();
    it.next();
    try {
      it.next();
      fail();
    } catch (NoSuchElementException ex) {
      // pass
    }

  }
  
  @Test(expected = UnsupportedOperationException.class)
  public void shouldNotSupportRemoveOperation() {
    BuilderIterable<String> testee = new BuilderIterable<String>(
        asString(integersFrom(1).limit(2)));
    testee.iterator().remove(); 
  }

}
