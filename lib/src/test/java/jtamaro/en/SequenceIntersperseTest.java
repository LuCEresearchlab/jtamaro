package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.Sequences.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SequenceIntersperseTest {

  //--- repeat
  @Test
  public void testIntersperseZero() {
    assertSequenceEquals(of(), intersperse("A", of()));
  }

  @Test
  public void testIntersperseOne() {
    assertSequenceEquals(of("1", "A", "2", "A", "3"), intersperse("A", of("1", "2", "3")));
  }

  @Test
  public void testIntersperseTwo() {
    assertSequenceEquals(of("X", "A", "Y"), intersperse("A", of("X", "Y")));
  }

  @Test
  public void testIntersperseThree() {
    assertSequenceEquals(of("X", "A", "Y", "A", "Z"), intersperse("A", of("X", "Y", "Z")));
  }

  @Test
  public void testIntersperseOfHasDefiniteSize() {
    assertTrue(intersperse("A", of("X", "Y")).hasDefiniteSize());
  }

  @Test
  public void testIntersperseRepeatHasDefiniteSize() {
    assertFalse(intersperse("A", repeat("X")).hasDefiniteSize());
  }

}
