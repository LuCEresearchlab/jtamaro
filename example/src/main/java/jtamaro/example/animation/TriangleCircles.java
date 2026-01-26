package jtamaro.example.animation;

import jtamaro.data.Sequence;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.range;
import static jtamaro.example.Toolbelt.equilateralTriangle;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.io.IO.animate;
import static jtamaro.io.IO.show;
import static jtamaro.io.IO.showFilmStrip;

public final class TriangleCircles {

  private TriangleCircles() {
  }

  public static void main() {
    show(equilateralTriangleWithInCircleAndCircumCircle(200));
    showFilmStrip(triangleCircleAnimation());
    animate(triangleCircleAnimation());
  }

  private static Graphic equilateralTriangleWithInCircleAndCircumCircle(double side) {
    final double circumRadius = side / Math.sqrt(3);
    final double inRadius = side / (2 * Math.sqrt(3));
    return compose(
        ellipse(inRadius * 2, inRadius * 2, BLACK),
        compose(
            equilateralTriangle(side, WHITE),
            ellipse(circumRadius * 2, circumRadius * 2, BLACK)
        )
    );
  }

  private static Sequence<Graphic> triangleCircleAnimation() {
    double side = 400;
    return range(0, 360, 3)
        .map((Integer a) -> rotate(a, equilateralTriangleWithInCircleAndCircumCircle(side)));
  }
}
