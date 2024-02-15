package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.Sequences.cycle;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.Sequences.take;
import static org.junit.Assert.assertFalse;

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
