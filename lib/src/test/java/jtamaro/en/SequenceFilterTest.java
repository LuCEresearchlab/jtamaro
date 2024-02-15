package jtamaro.en;

import org.junit.Test;

import static jtamaro.en.SequenceTest.assertSequenceEquals;
import static jtamaro.en.Sequences.filter;
import static jtamaro.en.Sequences.from;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.Sequences.range;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SequenceFilterTest {

  @Test
  public void testFilterEmptyTrue() {
    assertSequenceEquals(of(), filter(x -> true, of()));
  }

  @Test
  public void testFilterEmptyFalse() {
    assertSequenceEquals(of(), filter(x -> false, of()));
  }

  @Test
  public void testFilter1True() {
    assertSequenceEquals(of(1), filter(s -> true, of(1)));
  }

  @Test
  public void testFilter1False() {
    assertSequenceEquals(of(), filter(s -> false, of(1)));
  }

  @Test
  public void testFilter1TrueTest() {
    assertSequenceEquals(of(1), filter(s -> s == 1, of(1)));
  }

  @Test
  public void testFilter1FalseTest() {
    assertSequenceEquals(of(), filter(s -> s != 1, of(1)));
  }


  @Test
  public void testFilterMany() {
    assertSequenceEquals(of(0, 2, 4, 6, 8), filter(s -> s % 2 == 0, range(10)));
  }

  @Test
  public void testFilterDefiniteHasDefiniteSize() {
    assertTrue(filter(s -> true, range(10)).hasDefiniteSize());
  }

  @Test
  public void testFilterIndefiniteHasIndefiniteSize() {
    assertFalse(filter(s -> true, from(10)).hasDefiniteSize());
  }

}
