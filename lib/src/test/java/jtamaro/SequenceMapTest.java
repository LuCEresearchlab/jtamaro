package jtamaro;

import org.junit.jupiter.api.Test;

import static jtamaro.en.Sequences.*;

import static org.junit.jupiter.api.Assertions.*;
import static jtamaro.SequenceTest.*;


public class SequenceMapTest {

  @Test
  public void testMapEmpty() {
    assertSequenceEquals(of(), map(x->x, of()));
  }

  @Test
  public void testMap1() {
    assertSequenceEquals(of(3), map(s->s.length(), of("ABC")));
  }

  @Test
  public void testMap2() {
    assertSequenceEquals(of(3, 2), map(s->s.length(), of("ABC", "12")));
  }

  @Test
  public void testMap3() {
    assertSequenceEquals(of(3, 2, 4), map(s->s.length(), of("ABC", "12", "....")));
  }

  @Test
  public void testMapInfinite3() {
    assertSequenceEquals(of(19, 20, 21), take(3, map(s->s, from(19))));
  }

  @Test
  public void testMapDefiniteHasDefiniteSize() {
    assertTrue(map(s->s, range(10)).hasDefiniteSize());
  }

  @Test
  public void testMapIndefiniteHasIndefiniteSize() {
    assertFalse(map(s->s, from(10)).hasDefiniteSize());
  }
  
}
