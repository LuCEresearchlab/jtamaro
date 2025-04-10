package jtamaro.graphic;

import java.util.function.Function;
import org.junit.Assert;
import org.junit.Test;

public class CartesianWorldTest {

  @Test
  public void testAxesChangeSizeLessThanZeroPointFive() {
    final Graphic base = Graphics.triangle(10, 20, 30, Colors.RED);

    final CartesianWorld cw = new CartesianWorld()
        .withAxes(Colors.GREEN)
        .place(0, 0, base);
    final Graphic result = cw.asGraphic();

    Assert.assertNotEquals(base, result);
    Assert.assertEquals(base.getWidth(), result.getWidth(), 0.5);
    Assert.assertEquals(base.getHeight(), result.getHeight(), 0.5);
  }

  @Test
  public void testSizeChangesWithRespectToPlacement() {
    final double baseWidth = 50.0;
    final double baseHeight = 50.0;
    final Function<Color, Graphic> rect = c -> Graphics.rectangle(baseWidth, baseHeight, c);

    CartesianWorld cw = new CartesianWorld()
        .withAxes(Colors.MAGENTA)
        .place(0, 100, rect.apply(Colors.RED));
    final Graphic r1 = cw.asGraphic();
    Assert.assertEquals(baseWidth, r1.getWidth(), 0.001);
    // 0.5 delta to account for the x ax that sits at the bottom of the graphic now
    Assert.assertEquals(baseHeight / 2 + 100, r1.getHeight(), 0.5);

    cw = cw.place(100, 50, rect.apply(Colors.GREEN));
    final Graphic r2 = cw.asGraphic();
    Assert.assertNotEquals(r1, r2);
    Assert.assertEquals(r1.getWidth() + 100, r2.getWidth(), 0.001);
    // 0.5 delta to account for the x ax that sits at the bottom of the graphic now
    Assert.assertEquals(r1.getHeight(), r2.getHeight(), 0.5);

    cw = cw.place(-100, 150, rect.apply(Colors.BLUE));
    final Graphic r3 = cw.asGraphic();
    Assert.assertNotEquals(r1, r3);
    Assert.assertNotEquals(r2, r3);
    Assert.assertEquals(r2.getWidth() + 100, r3.getWidth(), 0.001);
    // 0.5 delta to account for the x ax that sits at the bottom of the graphic now
    Assert.assertEquals(r2.getHeight(), r2.getHeight(), 0.5);
  }
}
