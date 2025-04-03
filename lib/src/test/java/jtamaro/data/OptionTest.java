package jtamaro.data;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Assert;
import org.junit.Test;

public final class OptionTest {

  @Test
  public void someFlatMap() {
    Assert.assertEquals(Options.some(2), Options.some(1).flatMap(x -> Options.some(x + 1)));
    Assert.assertEquals(Options.none(), Options.some(1).flatMap($ -> Options.none()));
  }

  @Test
  public void noneFlatMap() {
    Assert.assertEquals(Options.none(), Options.none().flatMap($ -> Options.some(1)));
    Assert.assertEquals(Options.none(), Options.none().flatMap($ -> Options.none()));
  }

  @Test
  public void someMap() {
    Assert.assertEquals(Options.some(0), Options.some(1).map($ -> 0));
  }

  @Test
  public void noneMap() {
    Assert.assertEquals(Options.none(), Options.none().map($ -> 1));
  }

  @Test
  public void someFold() {
    final AtomicInteger i = new AtomicInteger(0);
    Assert.assertEquals(6, (long) Options.some(5).fold(x -> x + i.incrementAndGet(), () -> 0));
    Assert.assertEquals(1, i.get());
  }

  @Test
  public void noneFold() {
    final AtomicInteger i = new AtomicInteger(0);
    Assert.assertEquals(0, (long) new None<Integer>().fold(x -> x + i.incrementAndGet(), () -> 0));
    Assert.assertEquals(0, i.get());
  }

  @Test
  public void someIsNotEmpty() {
    Assert.assertFalse(Options.some(0).isEmpty());
  }

  @Test
  public void noneIsEmpty() {
    Assert.assertTrue(Options.none().isEmpty());
  }

  @Test
  public void someStreamLenOne() {
    Assert.assertEquals(1, Options.some(0).stream().count());
  }

  @Test
  public void noneStreamLenOne() {
    Assert.assertEquals(0, Options.none().stream().count());
  }
}
