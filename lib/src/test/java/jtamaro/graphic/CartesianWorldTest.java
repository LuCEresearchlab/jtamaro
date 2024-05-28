package jtamaro.graphic;

import java.util.function.Function;
import org.junit.Assert;
import org.junit.Test;

public class CartesianWorldTest {

  @Test
  public void testNegativePadding() {
    final Graphic base = Graphics.rectangle(10, 20, Colors.BLACK);

    final CartesianWorld cw = new CartesianWorld()
        .withPadding(-2)
        .place(0, 0, base);
    final Graphic result = cw.asGraphic();

    Assert.assertNotEquals(base, result);
    Assert.assertEquals(base.getWidth(), result.getWidth(), 0.001);
    Assert.assertEquals(base.getHeight(), result.getHeight(), 0.001);
  }

  @Test
  public void testPaddingWithColoredBg() {
    final Graphic base = Graphics.rectangle(10, 20, Colors.BLACK);

    final CartesianWorld cw = new CartesianWorld()
        .withPadding(5)
        .withBackground(Colors.WHITE)
        .place(0, 0, base);
    final Graphic result = cw.asGraphic();

    Assert.assertNotEquals(base, result);
    Assert.assertEquals(base.getWidth() + cw.getPadding() * 2, result.getWidth(), 0.001);
    Assert.assertEquals(base.getHeight() + cw.getPadding() * 2, result.getHeight(), 0.001);
  }

  @Test
  public void testPaddingWithTransparentBg() {
    final Graphic base = Graphics.ellipse(10, 20, Colors.BLUE);

    final CartesianWorld cw = new CartesianWorld()
        .withPadding(3)
        .withBackground(Colors.TRANSPARENT)
        .place(0, 0, base);
    final Graphic result = cw.asGraphic();

    Assert.assertNotEquals(base, result);
    Assert.assertEquals(base.getWidth() + cw.getPadding() * 2, result.getWidth(), 0.001);
    Assert.assertEquals(base.getHeight() + cw.getPadding() * 2, result.getHeight(), 0.001);
  }

  @Test
  public void testAxesDontChangeSize() {
    final Graphic base = Graphics.triangle(10, 20, 30, Colors.RED);

    final CartesianWorld cw = new CartesianWorld()
        .withAxes(Colors.GREEN)
        .place(0, 0, base);
    final Graphic result = cw.asGraphic();

    Assert.assertNotEquals(base, result);
    Assert.assertEquals(base.getWidth(), result.getWidth(), 0.001);
    Assert.assertEquals(base.getHeight(), result.getHeight(), 0.001);
  }

  @Test
  public void testSizeChangesWithRespectToPlacement() {
    final double baseWidth = 5.0;
    final double baseHeight = 5.0;
    final Function<Color, Graphic> rect = c -> Graphics.rectangle(baseWidth, baseHeight, c);

    CartesianWorld cw = new CartesianWorld()
        .withAxes(Colors.MAGENTA)
        .place(0, 10, rect.apply(Colors.RED));
    final Graphic r1 = cw.asGraphic();
    Assert.assertNotEquals(rect, r1);
    Assert.assertEquals(baseWidth, r1.getWidth(), 0.001);
    Assert.assertEquals(baseHeight / 2 + 10, r1.getHeight(), 0.001);

    cw = cw.place(10, 5, rect.apply(Colors.GREEN));
    final Graphic r2 = cw.asGraphic();
    Assert.assertNotEquals(rect, r2);
    Assert.assertNotEquals(r1, r2);
    Assert.assertEquals(r1.getWidth() / 2 + 10, r2.getWidth(), 0.001);
    Assert.assertEquals(r1.getHeight(), r2.getHeight(), 0.001);

    cw = cw.place(-10, 15, rect.apply(Colors.BLUE));
    final Graphic r3 = cw.asGraphic();
    Assert.assertNotEquals(rect, r3);
    Assert.assertNotEquals(r1, r3);
    Assert.assertNotEquals(r2, r3);
    Assert.assertEquals(r2.getWidth() + 10 + baseWidth / 2, r3.getWidth(), 0.001);
    Assert.assertEquals(r2.getHeight(), r2.getHeight(), 0.001);
  }
}
