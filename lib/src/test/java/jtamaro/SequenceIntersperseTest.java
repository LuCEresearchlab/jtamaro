package jtamaro;

import org.junit.jupiter.api.Test;

import static jtamaro.en.Sequences.*;

import static org.junit.jupiter.api.Assertions.*;
import static jtamaro.SequenceTest.*;


public class SequenceIntersperseTest {

  //--- repeat
  @Test
  public void testIntersperseZero() {
    assertSequenceEquals(of(), intersperse("A", of()));
  }

  public void testIntersperseOne() {
    assertSequenceEquals(of(), intersperse("A", of("X")));
  }

  public void testIntersperseTwo() {
    assertSequenceEquals(of("X", "A", "Y"), intersperse("A", of("X", "Y")));
  }

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
