package jtamaro;

import org.junit.jupiter.api.Test;

import static jtamaro.en.Sequences.*;

import static org.junit.jupiter.api.Assertions.*;
import static jtamaro.SequenceTest.*;


public class SequenceCycleTest {

  //--- cycle
  @Test
  public void testCycle() {
    assertSequenceEquals(of(1, 5, 1, 5, 1), take(5, cycle(of(1, 5))));
  }

  @Test
  public void testCycleString() {
    assertSequenceEquals(of("Hi", "Ho", "Hi"), take(3, cycle(of("Hi", "Ho"))));
  }

  @Test
  public void testCycleHasDefiniteSize() {
    assertFalse(cycle(of(1, 2)).hasDefiniteSize());
  }
}
