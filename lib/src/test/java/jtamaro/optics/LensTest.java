package jtamaro.optics;

import jtamaro.data.Function1;
import org.junit.Assert;
import org.junit.Test;

public final class LensTest {

  private static final Lens<Rec, Rec, Integer, Integer>
      LENS_REC_A = new Lens<>() {
    @Override
    public Rec over(Function1<Integer, Integer> lift, Rec source) {
      return new Rec(lift.apply(source.a), source.b);
    }

    @Override
    public Integer view(Rec source) {
      return source.a;
    }
  };

  private static final Lens<Integer, Integer, Double, Double>
      LENS_SQRT = new Lens<>() {
    @Override
    public Integer over(Function1<Double, Double> lift, Integer source) {
      return lift.apply(Double.valueOf(source)).intValue();
    }

    @Override
    public Double view(Integer source) {
      return Double.valueOf(source);
    }
  };

  record Rec(int a, int b) {

  }

  @Test
  public void getPut() {
    final Rec s = new Rec(16, 18);
    final Integer newValue = 49;

    Assert.assertEquals(
        "You get back what you put in",
        newValue,
        LENS_REC_A.view(LENS_REC_A.set(newValue, s))
    );
  }

  @Test
  public void putGet() {
    final Rec s = new Rec(15, 52);

    Assert.assertEquals(
        "Putting back what you got doesn’t change anything",
        s,
        LENS_REC_A.set(LENS_REC_A.view(s), s)
    );
  }

  @Test
  public void putPut() {
    final Rec s = new Rec(77, 9);
    final int newValue = 0;

    Assert.assertEquals(
        "Setting twice is the same as setting once",
        LENS_REC_A.set(newValue, s),
        LENS_REC_A.set(newValue, LENS_REC_A.set(newValue, s))
    );
  }

  @Test
  public void testComposeView() {
    final Rec s = new Rec(2, 7);

    Assert.assertEquals(
        LENS_SQRT.view(s.a),
        LENS_REC_A.then(LENS_SQRT).view(s)
    );
  }

  @Test
  public void testComposeOver() {
    final Rec s = new Rec(123454321, 0);
    final Function1<Double, Double> f = x -> -x;

    Assert.assertEquals(
        new Rec(LENS_SQRT.over(f, s.a), s.b),
        LENS_REC_A.then(LENS_SQRT).over(f, s)
    );
  }
}
