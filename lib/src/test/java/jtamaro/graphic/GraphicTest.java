package jtamaro.graphic;

import org.junit.Assert;
import org.junit.Test;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Points.BOTTOM_CENTER;

public final class GraphicTest {

  private static final double TEST_DELTA = 0.0001;

  @Test
  public void testAbove() {
    final Graphic a = new Rectangle(16, 18, Colors.MAGENTA);
    final Graphic b = new Rectangle(15, 52, Colors.GREEN);
    final Above above = new Above(a, b);

    Assert.assertEquals(a, above.getTop());
    Assert.assertEquals(b, above.getBottom());

    Assert.assertEquals(Math.max(a.getWidth(), b.getWidth()), above.getWidth(), TEST_DELTA);
    Assert.assertEquals(a.getHeight() + b.getHeight(), above.getHeight(), TEST_DELTA);

    Assert.assertEquals(above, Graphics.above(a, b));
  }

  @Test
  public void testBeside() {
    final Graphic a = new Rectangle(5, 8, Colors.BLUE);
    final Graphic b = new CircularSector(10, 30, Colors.RED);
    final Beside beside = new Beside(a, b);

    Assert.assertEquals(a, beside.getLeft());
    Assert.assertEquals(b, beside.getRight());

    Assert.assertEquals(a.getWidth() + b.getWidth(), beside.getWidth(), TEST_DELTA);
    Assert.assertEquals(Math.max(a.getHeight(), b.getHeight()), beside.getHeight(), TEST_DELTA);

    Assert.assertEquals(beside, Graphics.beside(a, b));
  }

  @Test
  public void testCircularSector() {
    final CircularSector circularSector = new CircularSector(5.0, 180, Colors.CYAN);

    Assert.assertEquals(5.0, circularSector.getRadius(), TEST_DELTA);
    Assert.assertEquals(180.0, circularSector.getAngle(), TEST_DELTA);
    Assert.assertEquals(Colors.CYAN, circularSector.getColor());

    Assert.assertEquals(10.0, circularSector.getWidth(), TEST_DELTA);
    Assert.assertEquals(5.0, circularSector.getHeight(), TEST_DELTA);

    Assert.assertEquals(circularSector, Graphics.circularSector(5.0, 180, Colors.CYAN));
  }

  @Test
  public void testCompose() {
    final Graphic a = new Pin(Points.TOP_LEFT, new Triangle(13.0, 27.0, 15.0, Colors.YELLOW));
    final Graphic b = new Pin(Points.BOTTOM_RIGHT, new Rectangle(18.0, 24.0, Colors.TRANSPARENT));
    final Compose compose = new Compose(a, b);

    Assert.assertEquals(a, compose.getForeground());
    Assert.assertEquals(b, compose.getBackground());

    Assert.assertEquals(a.getWidth() + b.getWidth(), compose.getWidth(), TEST_DELTA);
    Assert.assertEquals(a.getHeight() + b.getHeight(), compose.getHeight(), TEST_DELTA);

    Assert.assertEquals(compose, Graphics.compose(a, b));
  }

  @Test
  public void testEllipse() {
    final Ellipse ellipse = new Ellipse(5.0, 2.5, Colors.RED);

    Assert.assertEquals(5.0, ellipse.getWidth(), TEST_DELTA);
    Assert.assertEquals(2.5, ellipse.getHeight(), TEST_DELTA);
    Assert.assertEquals(Colors.RED, ellipse.getColor());

    Assert.assertEquals(ellipse, Graphics.ellipse(5.0, 2.5, Colors.RED));
  }

  @Test
  public void testEmptyGraphic() {
    final EmptyGraphic emptyGraphic = new EmptyGraphic();

    Assert.assertEquals(0.0, emptyGraphic.getWidth(), TEST_DELTA);
    Assert.assertEquals(0.0, emptyGraphic.getHeight(), TEST_DELTA);

    Assert.assertEquals(emptyGraphic, Graphics.emptyGraphic());
  }

  @Test
  public void testOverlay() {
    final Graphic a = new Rectangle(13.0, 37.0, Colors.WHITE);
    final Graphic b = new Ellipse(20.0, 4.0, Colors.BLACK);
    final Overlay overlay = new Overlay(a, b);

    Assert.assertEquals(a, overlay.getForeground());
    Assert.assertEquals(b, overlay.getBackground());

    Assert.assertEquals(Math.max(a.getWidth(), b.getWidth()), overlay.getWidth(), TEST_DELTA);
    Assert.assertEquals(Math.max(a.getHeight(), b.getHeight()), overlay.getHeight(), TEST_DELTA);

    Assert.assertEquals(overlay, Graphics.overlay(a, b));
  }

  @Test
  public void testPin() {
    final Graphic a = new CircularSector(14.0, 90.0, Colors.MAGENTA);
    final Pin pin = new Pin(Points.BOTTOM_LEFT, a);

    Assert.assertEquals(a, pin.getGraphic());
    Assert.assertEquals(Points.BOTTOM_LEFT, pin.getPinPoint());

    Assert.assertEquals(a.getWidth(), pin.getWidth(), TEST_DELTA);
    Assert.assertEquals(a.getHeight(), pin.getHeight(), TEST_DELTA);

    Assert.assertEquals(pin, Graphics.pin(Points.BOTTOM_LEFT, a));
  }

  @Test
  public void testRectangle() {
    final Rectangle rectangle = new Rectangle(16.0, 18.0, Colors.GREEN);

    Assert.assertEquals(16.0, rectangle.getWidth(), TEST_DELTA);
    Assert.assertEquals(18.0, rectangle.getHeight(), TEST_DELTA);
    Assert.assertEquals(Colors.GREEN, rectangle.getColor());

    Assert.assertEquals(rectangle, Graphics.rectangle(16.0, 18.0, Colors.GREEN));
  }

  @Test
  public void testRotate() {
    final Triangle t = new Triangle(10, 20, 115.9, Colors.BLUE);
    final Rotate rotate = new Rotate(22.45, t);

    Assert.assertEquals(22.45, rotate.getAngle(), TEST_DELTA);
    Assert.assertEquals(t, rotate.getGraphic());

    /*
     * Example with angle = 90
     *
     * C
     * |\
     * | \
     * A--B
     */
    final double radAngle = Math.toRadians(t.getAngle());
    final double aX = 0.0;
    final double aY = 0.0;
    final double bX = aX + t.getSide1();
    //noinspection UnnecessaryLocalVariable
    final double bY = aY;
    final double cX = aX + Math.cos(radAngle) * t.getSide2();
    final double cY = aY + Math.sin(radAngle) * t.getSide2();

    final double radRotAngle = Math.toRadians(rotate.getAngle());
    final double cosRotAngle = Math.cos(radRotAngle);
    final double sinRotAngle = Math.sin(radRotAngle);

    final double rotAx = cosRotAngle * aX - sinRotAngle * aY;
    final double rotAy = sinRotAngle * aX + cosRotAngle * aY;
    final double rotBx = cosRotAngle * bX - sinRotAngle * bY;
    final double rotBy = sinRotAngle * bX + cosRotAngle * bY;
    final double rotCx = cosRotAngle * cX - sinRotAngle * cY;
    final double rotCy = sinRotAngle * cX + cosRotAngle * cY;

    final double rotWidth = Math.abs(
        Math.min(Math.min(rotAx, rotBx), rotCx)
            - Math.max(Math.max(rotAx, rotBx), rotCx));
    final double rotHeight = Math.abs(
        Math.min(Math.min(rotAy, rotBy), rotCy)
            - Math.max(Math.max(rotAy, rotBy), rotCy));

    Assert.assertEquals(rotWidth,
        rotate.getWidth(),
        TEST_DELTA);
    Assert.assertEquals(rotHeight,
        rotate.getHeight(),
        TEST_DELTA);

    Assert.assertEquals(rotate, Graphics.rotate(22.45, t));
  }

  @Test
  public void testText() {
    final Text text = new Text("Hello there", Fonts.SERIF, 10.0, Colors.BLACK);

    Assert.assertEquals("Hello there", text.getContent());
    Assert.assertEquals(Fonts.SERIF, text.getFont());
    Assert.assertEquals(10.0, text.getSize(), TEST_DELTA);
    Assert.assertEquals(Colors.BLACK, text.getColor());

    Assert.assertEquals(text, Graphics.text("Hello there", Fonts.SERIF, 10.0, Colors.BLACK));
  }

  @Test
  public void testTriangle() {
    final Triangle triangle = new Triangle(20.0, 32.0, 71.0, Colors.YELLOW);

    Assert.assertEquals(20.0, triangle.getSide1(), TEST_DELTA);
    Assert.assertEquals(32.0, triangle.getSide2(), TEST_DELTA);
    Assert.assertEquals(71.0, triangle.getAngle(), TEST_DELTA);
    Assert.assertEquals(Colors.YELLOW, triangle.getColor());

    final double radAngle = Math.toRadians(triangle.getAngle());
    Assert.assertEquals(Math.max(triangle.getSide1(),
            triangle.getSide2() * Math.cos(radAngle)),
        triangle.getWidth(),
        TEST_DELTA);
    Assert.assertEquals(triangle.getSide2() * Math.sin(radAngle),
        triangle.getHeight(),
        TEST_DELTA);

    Assert.assertEquals(triangle, Graphics.triangle(20.0, 32.0, 71.0, Colors.YELLOW));
  }

  @Test
  public void testComposeRotationSize() {
    // Test for: https://github.com/LuCEresearchlab/jtamaro/issues/1
    final double outer = 200.0 / 3.0;
    final double inner = outer / 2.0;
    final Graphic g = rotate(
        9 * 5,
        compose(
            pin(BOTTOM_CENTER, ellipse(inner, inner, BLACK)),
            pin(BOTTOM_CENTER, ellipse(outer, outer, WHITE)))
    );

    Assert.assertEquals(outer, g.getWidth(), 0.0001);
    Assert.assertEquals(outer, g.getHeight(), 0.0001);
  }
}
