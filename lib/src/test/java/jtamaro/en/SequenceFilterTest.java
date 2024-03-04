package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.SequenceTest.consume;
import static jtamaro.en.Sequences.filter;
import static jtamaro.en.Sequences.from;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.Sequences.range;
import static jtamaro.en.Sequences.isEmpty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class SequenceFilterTest {

  @Test
  public void testFilterEmptyTrue() {
    assertSequenceEquals(of(), filter(x -> true, of()));
  }

  @Test
  public void testFilterEmptyFalse() {
    assertSequenceEquals(of(), filter(x -> false, of()));
  }

  @Test
  public void testFilter1True() {
    assertSequenceEquals(of(1), filter(s -> true, of(1)));
  }

  @Test
  public void testFilter1False() {
    assertSequenceEquals(of(), filter(s -> false, of(1)));
  }

  @Test
  public void testFilter1TrueTest() {
    assertSequenceEquals(of(1), filter(s -> s == 1, of(1)));
  }

  @Test
  public void testFilter1FalseTest() {
    assertSequenceEquals(of(), filter(s -> s != 1, of(1)));
  }


  @Test
  public void testFilterMany() {
    assertSequenceEquals(of(0, 2, 4, 6, 8), filter(s -> s % 2 == 0, range(10)));
  }


  //--- test lazy behavior
  @Test
  public void testFilterInfiniteNotConsumed() {
    filter(s -> false, from(0));
    // should be lazy and not consume anything, and thus not search through the infinitely long sequence
    assertTrue(true);
  }

  @Test
  public void testFilterInfiniteConsumed() {
    assertThrows(StackOverflowError.class, () -> {
      // filter through the infinitely long sequence to find an element to ask whether it's empty
      isEmpty(filter(s -> false, from(0)));
    });
  }


  //--- count number of filter predicate applications
  @Test
  public void testFilterPredicateApplicationCountNotConsumed() {
    int[] count = new int[] { 0 };
    filter(s -> { count[0]++; return true;}, range(10));
    assertEquals(0, count[0]);
  }

  @Test
  public void testFilterPredicateApplicationCountConsumed() {
    int[] count = new int[] { 0 };
    consume(filter(s -> { count[0]++; return true;}, range(10)));
    assertEquals(10, count[0]);
  }

  @Test
  public void testFilterFilterPredicateApplicationCountConsumed() {
    int[] innerCount = new int[] { 0 };
    int[] outerCount = new int[] { 0 };
    consume(filter(s1 -> { outerCount[0]++; return true;}, filter(s -> { innerCount[0]++; return true;}, range(10))));
    assertEquals(10, innerCount[0]);
    assertEquals(10, outerCount[0]);
  }


  //--- check definite/indefinite
  @Test
  public void testFilterDefiniteHasDefiniteSize() {
    assertTrue(filter(s -> true, range(10)).hasDefiniteSize());
  }

  @Test
  public void testFilterIndefiniteHasIndefiniteSize() {
    assertFalse(filter(s -> true, from(10)).hasDefiniteSize());
  }

}
