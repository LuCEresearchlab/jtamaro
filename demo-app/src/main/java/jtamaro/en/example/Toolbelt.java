package jtamaro.en.example;

import static jtamaro.en.Graphics.*;

import jtamaro.en.Color;
import jtamaro.en.Graphic;


/**
 * These are commonly used methods that we do not provide in the JTamaro library,
 * but which we expect students to develop themselves throughout a course.
 * We place them here so examples can use them.
 */
public final class Toolbelt {
  
  // prevent instantiation
  private Toolbelt() {
  }

  public static Graphic square(double side, Color color) {
    return rectangle(side, side, color);
  }

  public static Graphic circle(double diameter, Color color) {
    return ellipse(diameter, diameter, color);
  }


  public static Graphic isoscelesTriangle(double side, double angle, Color color) {
    return triangle(side, side, angle, color);
  }

  public static Graphic equilateralTriangle(double side, Color color) {
    return triangle(side, side, 60, color);
  }

  public static Graphic rightTriangle(double side1, double side2, Color color) {
    return triangle(side1, side2, 90, color);
  }

}
