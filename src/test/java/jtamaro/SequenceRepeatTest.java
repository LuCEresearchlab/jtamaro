package jtamaro;

import org.junit.jupiter.api.Test;

import static jtamaro.en.Sequences.*;

import static org.junit.jupiter.api.Assertions.*;
import static jtamaro.SequenceTest.*;


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
