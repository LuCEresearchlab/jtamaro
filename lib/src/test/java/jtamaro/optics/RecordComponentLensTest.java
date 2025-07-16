package jtamaro.optics;

import org.junit.Assert;
import org.junit.Test;

public final class RecordComponentLensTest {

  @Test
  public void testCompositeGet() {
    final Lens<R1, R1, Integer, Integer> r1sLens =
        new RecordComponentLens<>(R1.class, "i");
    final Lens<R2, R2, R1, R1> r2r1Lens =
        new RecordComponentLens<>(R2.class, "r1");

    final R2 initial = new R2(7, new R1(10, "10"));

    Assert.assertEquals(Integer.valueOf(10), r2r1Lens.then(r1sLens).view(initial));
  }

  @Test
  public void testCompositeSet() {
    final Lens<R1, R1, String, String> r1sLens =
        new RecordComponentLens<>(R1.class, "s");
    final Lens<R2, R2, R1, R1> r2r1Lens =
        new RecordComponentLens<>(R2.class, "r1");

    final R2 initial = new R2(16, new R1(18, "one"));
    final R2 actual = r2r1Lens.then(r1sLens).set("zero", initial);

    Assert.assertEquals(16, actual.i);
    Assert.assertEquals(18, actual.r1.i);
    Assert.assertEquals("zero", actual.r1.s);
  }

  record R1(int i, String s) {

  }

  record R2(int i, R1 r1) {

  }
}
