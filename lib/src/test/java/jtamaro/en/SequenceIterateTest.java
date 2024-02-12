package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.Sequences.iterate;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.Sequences.take;
import static org.junit.Assert.assertFalse;

public class SequenceIterateTest {

  //--- repeat
  @Test
  public void testIterate() {
    assertSequenceEquals(of(0, 1, 2), take(3, iterate(x -> x + 1, 0)));
  }

  @Test
  public void testIterateString() {
    assertSequenceEquals(of("A", "AA", "AAAA"), take(3, iterate(x -> x + x, "A")));
  }

  @Test
  public void testIterateHasDefiniteSize() {
    assertFalse(iterate(x -> x + 1, 0).hasDefiniteSize());
  }

}
