package jtamaro.data;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;
import org.junit.Test;

public final class EitherTest {

  @Test
  public void leftFlatMapLeft() {
    Assert.assertEquals(Eithers.right(0),
        Eithers.left(0).flatMapLeft(_ -> Eithers.right(0)));
  }

  @Test
  public void leftFlatMapRight() {
    Assert.assertEquals(Eithers.left(0),
        Eithers.left(0).flatMapRight(_ -> Eithers.right(0)));
  }

  @Test
  public void rightFlatMapLeft() {
    Assert.assertEquals(Eithers.right(0),
        Eithers.right(0).flatMapLeft(_ -> Eithers.left(0)));
  }

  @Test
  public void rightFlatMapRight() {
    Assert.assertEquals(Eithers.left(0),
        Eithers.right(0).flatMapRight(_ -> Eithers.left(0)));
  }

  @Test
  public void leftMapLeft() {
    Assert.assertEquals(Eithers.left(1), Eithers.left(0).mapLeft(_ -> 1));
  }

  @Test
  public void leftMapRight() {
    Assert.assertEquals(Eithers.left(0), Eithers.left(0).mapRight(_ -> 1));
  }

  @Test
  public void rightMapLeft() {
    Assert.assertEquals(Eithers.right(1), Eithers.right(1).mapLeft(_ -> 0));
  }

  @Test
  public void rightMapRight() {
    Assert.assertEquals(Eithers.right(0), Eithers.right(1).mapRight(_ -> 0));
  }

  @Test
  public void rightFold() {
    final AtomicInteger i = new AtomicInteger(0);
    Assert.assertEquals(6,
        (long) Eithers.right(5).fold(_ -> 0, x -> x + i.incrementAndGet()));
    Assert.assertEquals(1, i.get());
  }

  @Test
  public void leftFold() {
    final AtomicInteger i = new AtomicInteger(0);
    Assert.assertEquals(5, (long) Eithers.left(4).fold(x -> x + i.incrementAndGet(), _ -> 0));
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
  public void leftOptionLeft() {
    Assert.assertEquals(new Some<>(6), Eithers.left(6).optionLeft());
  }

  @Test
  public void leftOptionRight() {
    Assert.assertEquals(new None<>(), Eithers.left(9).optionRight());
  }

  @Test
  public void rightOptionLeft() {
    Assert.assertEquals(new None<>(), Eithers.right(7).optionLeft());
  }

  @Test
  public void rightOptionRight() {
    Assert.assertEquals(new Some<>(2), Eithers.right(2).optionRight());
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
