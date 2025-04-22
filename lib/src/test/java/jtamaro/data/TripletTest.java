package jtamaro.data;

import org.junit.Assert;
import org.junit.Test;

public final class TripletTest {

  @Test
  public void testWithFirst() {
    final Triplet<Double, Long, Boolean> t1 = new Triplet<>(Math.TAU, Long.MIN_VALUE, false);
    final Triplet<Boolean, Long, Boolean> t2 = t1.withFirst(false);
    Assert.assertFalse(t2.first());
    Assert.assertEquals(t2.second(), t1.second());
    Assert.assertEquals(t2.third(), t1.third());
  }

  @Test
  public void testWithSecond() {
    final Triplet<Double, Integer, String> t1 = new Triplet<>(Math.E, 0, "");
    final Triplet<Double, long[], String> t2 = t1.withSecond(new long[t1.second()]);
    Assert.assertEquals(t1.first(), t2.first(), 0.00001);
    Assert.assertArrayEquals(new long[0], t2.second());
    Assert.assertEquals(t1.third(), t2.third());
  }

  @Test
  public void testWithThird() {
    final Triplet<Double, Long, Boolean> t1 = new Triplet<>(Math.TAU, Long.MIN_VALUE, false);
    final Triplet<Double, Long, String> t2 = t1.withThird("Hello");
    Assert.assertEquals(Math.TAU, t2.first(), 0.00001);
    Assert.assertEquals(t2.second(), t1.second());
    Assert.assertEquals("Hello", t2.third());
  }

}
