package jtamaro.en.example.animation;

import jtamaro.en.Sequence;
import jtamaro.en.Graphic;

import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.example.Toolbelt.*;


public class TriangleCircles {

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
    double circumRadius = side / Math.sqrt(3);
    double size = 2 * circumRadius;
    return map(
      (Integer a) -> rotate(a, equilateralTriangleWithInCircleAndCircumCircle(side)),
      range(0, 360, 3)
    );
  }


  public static void main(String[] args) {
    show(equilateralTriangleWithInCircleAndCircumCircle(200));
    showFilmStrip(triangleCircleAnimation());
    animate(triangleCircleAnimation());
  }

}
