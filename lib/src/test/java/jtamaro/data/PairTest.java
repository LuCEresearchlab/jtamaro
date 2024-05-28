package jtamaro.data;

import org.junit.Assert;
import org.junit.Test;

public class PairTest {

  @Test
  public void testWithFirst() {
    final Pair<Double, Long> p1 = new Pair<>(Math.TAU, Long.MIN_VALUE);
    final Pair<Boolean, Long> p2 = p1.withFirst(false);
    Assert.assertFalse(p2.first());
    Assert.assertEquals(p2.second(), p1.second());
  }

  @Test
  public void testWithSecond() {
    final Pair<Double, Integer> p1 = new Pair<>(Math.E, 0);
    final Pair<Double, long[]> p2 = p1.withSecond(new long[p1.second()]);
    Assert.assertEquals(p1.first(), p2.first(), 0.00001);
    Assert.assertArrayEquals(new long[0], p2.second());
  }
}
