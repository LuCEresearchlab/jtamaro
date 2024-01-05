package jtamaro.en.example.graphic;

import jtamaro.en.Graphic;

import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;
import static jtamaro.en.example.Toolbelt.*;


public class TriangleDemo {

  private static Graphic kindsOfTriangles() {
    return above(
        above(
            isoscelesTriangle(200, 30, RED),
            rightTriangle(200, 100, GREEN)
        ),
        equilateralTriangle(200, BLUE)
    );
  }

  private static Graphic triangleVsSector(double side, double angle) {
    return overlay(
        isoscelesTriangle(side, angle, RED),
        circularSector(side, angle, BLUE)
    );
  }

  public static void main(String[] args) {
    show(rotate(45, triangle(200, 100, 90, RED)));
    show(kindsOfTriangles());
    show(triangleVsSector(200, 45));
  }

}
