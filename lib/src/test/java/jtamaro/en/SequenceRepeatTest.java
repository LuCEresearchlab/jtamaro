package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.Sequences.repeat;
import static jtamaro.en.Sequences.take;
import static org.junit.Assert.assertFalse;

public class SequenceRepeatTest {

  //--- repeat
  @Test
  public void testRepeat() {
    assertSequenceEquals(of(5, 5, 5), take(3, repeat(5)));
  }

  @Test
  public void testRepeatString() {
    assertSequenceEquals(of("Hi", "Hi", "Hi"), take(3, repeat("Hi")));
  }

  @Test
  public void testRepeatHasDefiniteSize() {
    assertFalse(repeat(5).hasDefiniteSize());
  }
}
