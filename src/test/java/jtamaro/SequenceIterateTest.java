package jtamaro;

import org.junit.jupiter.api.Test;

import static jtamaro.en.Sequences.*;

import static org.junit.jupiter.api.Assertions.*;
import static jtamaro.SequenceTest.*;


public class SequenceIterateTest {

  //--- repeat
  @Test
  public void testIterate() {
    assertSequenceEquals(of(0, 1, 2), take(3, iterate(x->x+1, 0)));
  }

  public void testIterateString() {
    assertSequenceEquals(of("A", "AA", "AAA"), take(3, iterate(x->x+1, "A")));
  }

  @Test
  public void testIterateHasDefiniteSize() {
    assertFalse(iterate(x->x+1, 0).hasDefiniteSize());
  }
  
}
