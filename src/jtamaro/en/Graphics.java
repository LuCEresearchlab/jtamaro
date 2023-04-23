package jtamaro.en;

import jtamaro.en.graphic.*;


public final class Graphics {

  // prevent instantiation
  private Graphics() {
  }


  //-- nullary graphic operations
  public static Graphic emptyGraphic() {
    return new EmptyGraphic();
  }

  /**
   * Create a circular sector with the given radius and angle.
   *
   * @param radius the radius of the circular sector
   * @param angle  the angle of the circular sector in degrees, positive values rotate counter-clockwise, negative values rotate clockwise
   * @param color  the color of the circular sector
   * @return a circular sector
   */
  public static Graphic circularSector(double radius, double angle, Color color) {
    return new CircularSector(radius, angle, color);
  }

  public static Graphic ellipse(double width, double height, Color color) {
    return new Ellipse(width, height, color);
  }

  public static Graphic rectangle(double width, double height, Color color) {
    return new Rectangle(width, height, color);
  }

  public static Graphic text(String content, String font, double points, Color color) {
    return new Text(content, font, points, color);
  }

  //OLD
  public static Graphic equilateralTriangle(double side, Color color) {
    return new EquilateralTriangle(side, color);
  }

  /**
   * Create a triangle specified using two sides and the angle between them.
   * The specified angle is at the bottom-left corner of the triangle,
   * while the first side extends horizontally to the right.
   * Its pinning position is the centroid of the triangle.
   *
   * @param side1 horizontal side
   * @param side2 side opposite to the angle
   * @param angle angle of rotation in degrees, positive values rotate counter-clockwise, negative values rotate clockwise
   * @param color color of the triangle
   * @return a triangle
   */
  public static Graphic triangle(double side1, double side2, double angle, Color color) {
    return new Triangle(side1, side2, angle, color);
  }


  //-- unary graphic operations

  /**
   * Rotate the given graphic by the given angle.
   *
   * @param degrees angle or rotation in degrees, positive values rotate counter-clockwise, negative values rotate clockwise
   * @param graphic the graphic to rotate
   * @return a rotated graphic
   */
  public static Graphic rotate(double degrees, Graphic graphic) {
    return new Rotate(degrees, graphic);
  }

  public static Graphic pin(Point point, Graphic graphic) {
    return new Pin(point, graphic);
  }


  //-- binary graphic operations
  public static Graphic compose(Graphic foregroundGraphic, Graphic backgroundGraphic) {
    return new Compose(foregroundGraphic, backgroundGraphic);
  }

  public static Graphic overlay(Graphic foregroundGraphic, Graphic backgroundGraphic) {
    return new Overlay(foregroundGraphic, backgroundGraphic);
  }

  public static Graphic beside(Graphic leftGraphic, Graphic rightGraphic) {
    return new Beside(leftGraphic, rightGraphic);
  }

  public static Graphic above(Graphic topGraphic, Graphic bottomGraphic) {
    return new Above(topGraphic, bottomGraphic);
  }

}
