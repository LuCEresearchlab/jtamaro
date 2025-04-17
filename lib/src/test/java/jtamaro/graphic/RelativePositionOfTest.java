package jtamaro.graphic;

import jtamaro.data.Function1;
import jtamaro.data.Option;
import org.junit.Assert;
import org.junit.Test;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.GREEN;
import static jtamaro.graphic.Colors.MAGENTA;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Fonts.MONOSPACED;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.graphic.Graphics.triangle;

public class RelativePositionOfTest {

  private static final Graphic TEST_GRAPHIC = above(
      beside(
          rectangle(100, 200, RED),
          ellipse(150, 150, BLUE)
      ),
      beside(
          rotate(
              180,
              triangle(100, 100, 60, GREEN)
          ),
          above(
              text("Hello", MONOSPACED, 20, BLACK),
              circularSector(200, 200, MAGENTA)
          )
      )
  );
  private static final double HALF_HEIGHT = TEST_GRAPHIC.getHeight() / 2.0;
  private static final double HALF_WIDTH = TEST_GRAPHIC.getWidth() / 2.0;

  @Test
  public void testClickRedRectangle() {
    final Option<RelativeLocation> result = clickOn(178, 131);
    Assert.assertFalse(result.isEmpty());
    final RelativeLocation relLoc = result.fold(Function1.identity(), () -> null);
    Assert.assertEquals(3, relLoc.x(), 0.01);
    Assert.assertEquals(31, relLoc.y(), 0.01);
    Assert.assertTrue(relLoc.graphic() instanceof Rectangle rect
        && RED.equals(rect.getColor()));
  }

  @Test
  public void testClickCircle() {
    final Option<RelativeLocation> result = clickOn(310, 171);
    Assert.assertFalse(result.isEmpty());
    final RelativeLocation relLoc = result.fold(Function1.identity(), () -> null);
    Assert.assertEquals(10, relLoc.x(), 0.01);
    Assert.assertEquals(71, relLoc.y(), 0.01);
    Assert.assertTrue(relLoc.graphic() instanceof Ellipse circle
        && BLUE.equals(circle.getColor()));
  }

  @Test
  public void testClickRotatedTriangle() {
    final Option<RelativeLocation> result = clickOn(92, 299);
    Assert.assertFalse(result.isEmpty());
    final RelativeLocation relLoc = result.fold(Function1.identity(), () -> null);
    Assert.assertEquals(-42, relLoc.x(), 0.01);
    Assert.assertEquals(28.47, relLoc.y(), 0.01);
    Assert.assertTrue(relLoc.graphic() instanceof Triangle triangle
        && GREEN.equals(triangle.getColor()));
  }

  @Test
  public void testClickCircularSector() {
    final Option<RelativeLocation> result = clickOn(113, 478);
    Assert.assertFalse(result.isEmpty());
    final RelativeLocation relLoc = result.fold(Function1.identity(), () -> null);
    Assert.assertEquals(-187, relLoc.x(), 0.01);
    Assert.assertEquals(62.58, relLoc.y(), 0.01);
    Assert.assertTrue(relLoc.graphic() instanceof CircularSector sector
        && MAGENTA.equals(sector.getColor()));
  }

  @Test
  public void testClickText() {
    final Option<RelativeLocation> result = clickOn(279, 208);
    Assert.assertFalse(result.isEmpty());
    final RelativeLocation relLoc = result.fold(Function1.identity(), () -> null);
    Assert.assertEquals(7.74, relLoc.x(), 0.01);
    Assert.assertEquals(-7.2, relLoc.y(), 0.01);
    Assert.assertTrue(relLoc.graphic() instanceof Text text
        && BLACK.equals(text.getColor()));
  }

  @Test
  public void testClickOutside() {
    final Option<RelativeLocation> result = clickOn(345, 459);
    Assert.assertTrue(result.isEmpty());
  }

  private Option<RelativeLocation> clickOn(double x, double y) {
    return TEST_GRAPHIC.relativeLocationOf(
        x - HALF_WIDTH,
        y - HALF_HEIGHT
    );
  }
}
