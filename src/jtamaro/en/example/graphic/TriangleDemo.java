package jtamaro.en.example.graphic;

import jtamaro.en.Color;
import jtamaro.en.Graphic;

import static jtamaro.en.Sequences.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Points.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.IO.*;


public class TriangleDemo {

  private static Graphic equilateralTriangle(double side, Color color) {
    return triangle(side, side, 60, color);
  }

  private static Graphic isoscelesTriangle(double side, double angle, Color color) {
    return triangle(side, side, angle, color);
  }

  private static Graphic rightTriangle(double side1, double side2, Color color) {
    return triangle(side1, side2, 90, color);
  }

  private static Graphic kindsOfTriangles() {
    return above(
      above(
        isoscelesTriangle(200, 30, RED),
        rightTriangle(200, 100, GREEN)
      ),
      equilateralTriangle(200, BLUE)
    );
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

  private static void triangleCircleAnimation() {
    double side = 400;
    double circumRadius = side / Math.sqrt(3);
    double size = 2 * circumRadius;
    animate(
      cons(
        rectangle(size, size, TRANSPARENT), // first frame to set canvas size
        cycle(
          map(
            (Integer a) -> rotate(a, equilateralTriangleWithInCircleAndCircumCircle(side)),
            range(0, 360, 3)
          )
        )
      )
    );
  }


  private static Graphic triangleVsSector(double side, double angle) {
    return overlay(
      isoscelesTriangle(side, angle, RED),
      circularSector(side, angle, BLUE)
    );
  }


  private static Graphic triangles(double angle) {
    return compose(
      ellipse(5, 5, RED),
      compose(
        triangle(200, 200, angle, GREEN), 
        triangle(250, 250, angle, BLUE)
      )
    );
  }

  private static void angleAnimation() {
    animate(
      cons(
        rectangle(500, 250, TRANSPARENT), // first frame to set canvas size
        cycle(
          map(a -> triangles(a), range(180))
        )
      )
    );
  }

  private static Graphic circleSegments(double radius, int sides) {
    return reduce(
      (Graphic a, Graphic b) -> compose(a, b),
      emptyGraphic(),
      map(
        i -> rotate(i * 360.0 / sides, circularSector(radius, 360.0 / sides, hsv(i * 360.0 / sides, 1, 1))),
        range(sides)
      )
    );
  }

  private static Graphic regularPolygon(double radius, int sides) {
    assert radius >= 0;
    assert sides >= 3;
    return reduce(
      (Graphic a, Graphic b) -> compose(a, b),
      emptyGraphic(),
      map(
        i -> rotate(i * 360.0 / sides, pin(BOTTOM_LEFT, triangle(radius, radius, 360.0 / sides, hsv(i * 360.0 / sides, 1, 1)))),
        range(sides)
      )
    );
  }

  private static void polygonAnimation() {
    animate(
      cons(
        rectangle(500, 500, TRANSPARENT), // first frame to set canvas size
        cycle(
          map(a -> regularPolygon(250, a), range(3, 24))
        )
      ),
      true,
      1000
    );
  }


  public static void main(String[] args) {
    show(kindsOfTriangles());
    show(triangleVsSector(200, 45));
    angleAnimation();
    show(equilateralTriangleWithInCircleAndCircumCircle(200));
    show(regularPolygon(200, 5));
    polygonAnimation();
    show(rotate(45, triangle(200, 100, 90, RED)));
    triangleCircleAnimation();
  }

}
