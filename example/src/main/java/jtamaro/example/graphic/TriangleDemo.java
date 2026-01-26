package jtamaro.example.graphic;

import jtamaro.graphic.Graphic;

import static jtamaro.example.Toolbelt.isoscelesTriangle;
import static jtamaro.example.Toolbelt.rightTriangle;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.GREEN;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.triangle;
import static jtamaro.io.IO.show;

public final class TriangleDemo {

  private TriangleDemo() {
  }

  public static void main() {
    show(rotate(45, triangle(200, 100, 90, RED)));
    show(kindsOfTriangles());
    show(triangleVsSector(200, 45));
  }

  private static Graphic kindsOfTriangles() {
    return above(
        isoscelesTriangle(200, 30, RED),
        rightTriangle(200, 100, GREEN)
    );
  }

  private static Graphic triangleVsSector(double side, double angle) {
    return overlay(
        isoscelesTriangle(side, angle, RED),
        circularSector(side, angle, BLUE)
    );
  }
}
