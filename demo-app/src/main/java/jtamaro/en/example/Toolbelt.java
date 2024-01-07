package jtamaro.en.example;

import static jtamaro.en.Sequences.*;
import static jtamaro.en.Colors.TRANSPARENT;
import static jtamaro.en.Graphics.*;

import jtamaro.en.Sequence;
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

  public static <T> int length(Sequence<T> s) {
    return isEmpty(s) ? 0 : 1 + length(rest(s));
  }

  // flatten
  public static <T> Sequence<T> concats(Sequence<Sequence<T>> nestedSequence) {
    return reduce(
      empty(),
      (e, a) -> concat(e, a),
      nestedSequence
    );
  }

  public static Graphic composes(Sequence<Graphic> graphics) {
    return reduce(
      emptyGraphic(),
      (e, a) -> compose(e, a),
      graphics
    );
  }

  public static Graphic besides(Sequence<Graphic> graphics) {
    return reduce(
      emptyGraphic(),
      (e, a) -> beside(e, a),
      graphics
    );
  }

  public static Graphic aboves(Sequence<Graphic> graphics) {
    return reduce(
      emptyGraphic(),
      (e, a) -> above(e, a),
      graphics
    );
  }

  public static Graphic overlays(Sequence<Graphic> graphics) {
    return reduce(
      emptyGraphic(),
      (e, a) -> overlay(e, a),
      graphics
    );
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


  public static Graphic hgap(double width) {
    return rectangle(width, 0, TRANSPARENT);
  }
    
  public static Graphic vgap(double height) {
    return rectangle(0, height, TRANSPARENT);
  }

}
