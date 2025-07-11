package jtamaro.data;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;
import org.junit.Test;

public final class EitherTest {

  @Test
  public void leftFlatMapLeft() {
    Assert.assertEquals(Eithers.right(0),
        Eithers.left(0).flatMapLeft($ -> Eithers.right(0)));
  }

  @Test
  public void leftFlatMapRight() {
    Assert.assertEquals(Eithers.left(0),
        Eithers.left(0).flatMapRight($ -> Eithers.right(0)));
  }

  @Test
  public void rightFlatMapLeft() {
    Assert.assertEquals(Eithers.right(0),
        Eithers.right(0).flatMapLeft($ -> Eithers.left(0)));
  }

  @Test
  public void rightFlatMapRight() {
    Assert.assertEquals(Eithers.left(0),
        Eithers.right(0).flatMapRight($ -> Eithers.left(0)));
  }

  @Test
  public void leftMapLeft() {
    Assert.assertEquals(Eithers.left(1), Eithers.left(0).mapLeft($ -> 1));
  }

  @Test
  public void leftMapRight() {
    Assert.assertEquals(Eithers.left(0), Eithers.left(0).mapRight($ -> 1));
  }

  @Test
  public void rightMapLeft() {
    Assert.assertEquals(Eithers.right(1), Eithers.right(1).mapLeft($ -> 0));
  }

  @Test
  public void rightMapRight() {
    Assert.assertEquals(Eithers.right(0), Eithers.right(1).mapRight($ -> 0));
  }

  @Test
  public void rightFold() {
    final AtomicInteger i = new AtomicInteger(0);
    Assert.assertEquals(6,
        (long) Eithers.right(5).fold($ -> 0, x -> x + i.incrementAndGet()));
    Assert.assertEquals(1, i.get());
  }

  @Test
  public void leftFold() {
    final AtomicInteger i = new AtomicInteger(0);
    Assert.assertEquals(5, (long) Eithers.left(4).fold(x -> x + i.incrementAndGet(), $ -> 0));
    Assert.assertEquals(1, i.get());
  }

  @Test
  public void leftIsNotRight() {
    Assert.assertFalse(Eithers.left(0).isRight());
  }

  @Test
  public void rightIsRight() {
    Assert.assertTrue(Eithers.right(0).isRight());
  }

  @Test
  public void leftStreamLeftLenOne() {
    Assert.assertEquals(1, Eithers.left(0).streamLeft().count());
  }

  @Test
  public void leftStreamRightLenZero() {
    Assert.assertEquals(0, Eithers.left(0).streamRight().count());
  }

  @Test
  public void rightStreamLeftLenZero() {
    Assert.assertEquals(0, Eithers.right(0).streamLeft().count());
  }

  @Test
  public void rightStreamRightLenOne() {
    Assert.assertEquals(1, Eithers.right(0).streamRight().count());
  }
}
