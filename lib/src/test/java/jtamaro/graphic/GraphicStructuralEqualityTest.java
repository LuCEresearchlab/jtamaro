package jtamaro.graphic;

import org.junit.Assert;
import org.junit.Test;

import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Fonts.SANS_SERIF;
import static jtamaro.graphic.Fonts.SERIF;
import static jtamaro.graphic.Points.BOTTOM_CENTER;
import static jtamaro.graphic.Points.CENTER;
import static jtamaro.graphic.Points.CENTER_LEFT;
import static jtamaro.graphic.Points.CENTER_RIGHT;
import static jtamaro.graphic.Points.TOP_CENTER;
import static jtamaro.graphic.Points.TOP_RIGHT;

public class GraphicStructuralEqualityTest {

  private static final Graphic CHILD_A = new Rectangle(10, 20, RED);

  private static final Graphic CHILD_B = new Ellipse(20, 10, BLUE);

  @Test
  public void testAbove() {
    final Graphic target = new Above(CHILD_A, CHILD_B);

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Above(CHILD_A, CHILD_B)));
    Assert.assertTrue(target.structurallyEqualTo(new Pin(
            CENTER,
            new Compose(
                new Pin(BOTTOM_CENTER, CHILD_A),
                new Pin(TOP_CENTER, CHILD_B)
            )
        )
    ));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new Above(CHILD_B, CHILD_A)));
  }

  @Test
  public void testActionable() {
    final Graphic target = new Actionable<>(CHILD_A)
        .withMouseMoveHandler((c, b) -> c)
        .asGraphic();

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Actionable<>(CHILD_A)
        .withMouseMoveHandler((c, b) -> b)
        .asGraphic()));
    Assert.assertTrue(target.structurallyEqualTo(new Actionable<>(CHILD_A)
        .asGraphic()));
    Assert.assertTrue(target.structurallyEqualTo(CHILD_A));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
  }

  @Test
  public void testBeside() {
    final Graphic target = new Beside(CHILD_A, CHILD_B);

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Beside(CHILD_A, CHILD_B)));
    Assert.assertTrue(target.structurallyEqualTo(new Pin(
            CENTER,
            new Compose(
                new Pin(CENTER_RIGHT, CHILD_A),
                new Pin(CENTER_LEFT, CHILD_B)
            )
        )
    ));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new Beside(CHILD_B, CHILD_A)));
  }

  @Test
  public void testCircularSector() {
    final Graphic target = new CircularSector(5, 30, RED);

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new CircularSector(5, 30, RED)));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new CircularSector(15, 30, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new CircularSector(5, 31, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new CircularSector(5, 30, BLUE)));
  }

  @Test
  public void testCompose() {
    final Graphic target = new Compose(CHILD_A, CHILD_B);

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Compose(CHILD_A, CHILD_B)));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new Compose(CHILD_B, CHILD_A)));
  }

  @Test
  public void testEllipse() {
    final Graphic target = new Ellipse(1, 2, RED);

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Ellipse(1, 2, RED)));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new Ellipse(2, 2, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new Ellipse(1, 1, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new Ellipse(1, 2, BLUE)));
  }

  @Test
  public void testEmptyGraphic() {
    final Graphic target = new EmptyGraphic();

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new EmptyGraphic()));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
  }

  @Test
  public void testOverlay() {
    final Graphic target = new Overlay(CHILD_A, CHILD_B);

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Overlay(CHILD_A, CHILD_B)));
    // TODO: maybe allow pin(CENTER, compose(...)) parent too?
    Assert.assertTrue(target.structurallyEqualTo(new Compose(
            new Pin(CENTER, CHILD_A),
            new Pin(CENTER, CHILD_B)
        )
    ));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new Beside(CHILD_B, CHILD_A)));
  }

  @Test
  public void testPin() {
    final Graphic target = new Pin(TOP_RIGHT, CHILD_A);
    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Pin(TOP_RIGHT, CHILD_A)));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new Pin(TOP_CENTER, CHILD_A)));
    Assert.assertFalse(target.structurallyEqualTo(new Pin(TOP_RIGHT, CHILD_B)));
  }

  @Test
  public void testRectangle() {
    final Graphic target = new Rectangle(1, 2, RED);

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Rectangle(1, 2, RED)));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new Rectangle(2, 2, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new Rectangle(1, 1, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new Rectangle(1, 2, BLUE)));
  }

  @Test
  public void testRotate() {
    final Graphic target = new Rotate(60, CHILD_A);
    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Rotate(60, CHILD_A)));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new Rotate(30, CHILD_A)));
    Assert.assertFalse(target.structurallyEqualTo(new Rotate(60, CHILD_B)));
  }

  @Test
  public void testText() {
    final Graphic target = new Text("Hello", SANS_SERIF, 100, RED);

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Text("Hello", SANS_SERIF, 100, RED)));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new Text("Hallo", SANS_SERIF, 100, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new Text("Hello", SERIF, 100, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new Text("Hello", SANS_SERIF, 0, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new Text("Hello", SANS_SERIF, 100, BLUE)));
  }

  @Test
  public void testTriangle() {
    final Graphic target = new Triangle(7, 8, 80, RED);

    Assert.assertTrue(target.structurallyEqualTo(target));
    Assert.assertTrue(target.structurallyEqualTo(new Triangle(7, 8, 80, RED)));

    Assert.assertFalse(target.structurallyEqualTo(CHILD_A));
    Assert.assertFalse(target.structurallyEqualTo(CHILD_B));
    Assert.assertFalse(target.structurallyEqualTo(new Triangle(4, 8, 80, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new Triangle(7, 6, 80, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new Triangle(7, 8, 9, RED)));
    Assert.assertFalse(target.structurallyEqualTo(new Triangle(7, 8, 80, BLUE)));
  }
}
