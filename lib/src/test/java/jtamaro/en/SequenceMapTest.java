package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.SequenceTest.consume;
import static jtamaro.en.Sequences.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


public class SequenceMapTest {

  @Test
  public void testMapEmpty() {
    assertSequenceEquals(of(), map(x -> x, of()));
  }

  @Test
  public void testMap1() {
    assertSequenceEquals(of(3), map(String::length, of("ABC")));
  }

  @Test
  public void testMap2() {
    assertSequenceEquals(of(3, 2), map(String::length, of("ABC", "12")));
  }

  @Test
  public void testMap3() {
    assertSequenceEquals(of(3, 2, 4), map(String::length, of("ABC", "12", "....")));
  }

  @Test
  public void testMapInfinite3() {
    assertSequenceEquals(of(19, 20, 21), take(3, map(s -> s, from(19))));
  }


  //--- test lazy behavior
  @Test
  public void testMapInfiniteNotConsumed() {
    map(e -> e, from(0));
    // should be lazy and not consume anything, and thus not search through the infinitely long sequence
    assertTrue(true);
  }

  @Test
  public void testMapInfiniteConsumed() {
    assertThrows(StackOverflowError.class, () -> {
      // go through the infinitely long sequence to map all elements
      consume(map(e -> e, from(0)));
    });
  }


  //--- count number of map function applications
  @Test
  public void testMapApplicationCountNotConsumed() {
    int[] count = new int[] { 0 };
    map(e -> { count[0]++; return e;}, range(10));
    assertEquals(0, count[0]);
  }

  @Test
  public void testMapApplicationCountConsumed() {
    int[] count = new int[] { 0 };
    consume(map(e -> { count[0]++; return e;}, range(10)));
    assertEquals(10, count[0]);
  }

  @Test
  public void testMapMapApplicationCountConsumed() {
    int[] innerCount = new int[] { 0 };
    int[] outerCount = new int[] { 0 };
    consume(map(e1 -> { outerCount[0]++; return e1;}, map(e -> { innerCount[0]++; return e;}, range(10))));
    assertEquals(10, innerCount[0]);
    assertEquals(10, outerCount[0]);
  }


  //--- check definite/indefinite
  @Test
  public void testMapDefiniteHasDefiniteSize() {
    assertTrue(map(s -> s, range(10)).hasDefiniteSize());
  }

  @Test
  public void testMapIndefiniteHasIndefiniteSize() {
    assertFalse(map(s -> s, from(10)).hasDefiniteSize());
  }

}
