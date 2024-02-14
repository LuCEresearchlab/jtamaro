package jtamaro.en;

import jtamaro.en.graphic.*;


/**
 * Static methods for working with graphics.
 *
 * @see jtamaro.en.Graphic
 */
public final class Graphics {

  // prevent instantiation
  private Graphics() {
  }


  //-- nullary graphic operations
  public static Graphic emptyGraphic() {
    return new EmptyGraphic();
  }

  /**
   * Create a circular sector.
   *
   * @param radius the radius of the circular sector
   * @param angle  the angle of the circular sector in degrees, positive values rotate counter-clockwise,
   *               negative values rotate clockwise
   * @param color  the color of the circular sector
   * @return a circular sector
   */
  public static Graphic circularSector(double radius, double angle, Color color) {
    assert radius >= 0 : "radius cannot be negative";
    return new CircularSector(radius, angle, color);
  }

  /**
   * Create an axis-aligned ellipse.
   *
   * @param width  the width of the ellipse
   * @param height the height of the ellipse
   * @param color  the color of the ellipse
   * @return an ellipse
   */
  public static Graphic ellipse(double width, double height, Color color) {
    assert width >= 0 : "width cannot be negative";
    assert height >= 0 : "height cannot be negative";
    return new Ellipse(width, height, color);
  }

  /**
   * Create an axis-aligned rectangle.
   *
   * @param width  the width of the rectangle
   * @param height the height of the rectangle
   * @param color  the color of the rectangle
   * @return a rectangle
   */
  public static Graphic rectangle(double width, double height, Color color) {
    return new Rectangle(width, height, color);
  }

  /**
   * Create a text.
   *
   * @param content the actual text to render
   * @param font    the name of the font to use to render the text
   * @param points  the font size in points
   * @param color   the color of the text
   * @return a text
   */
  public static Graphic text(String content, String font, double points, Color color) {
    return new Text(content, font, points, color);
  }

  //OLD
  //public static Graphic equilateralTriangle(double side, Color color) {
  //  return new EquilateralTriangle(side, color);
  //}

  /**
   * Create a triangle specified using two sides and the angle between them.
   * The specified angle is at the bottom-left corner of the triangle,
   * while the first side extends horizontally to the right.
   * Its pinning position is the centroid of the triangle.
   *
   * @param side1 horizontal side
   * @param side2 side opposite to the angle
   * @param angle angle of rotation in degrees, positive values rotate counter-clockwise,
   *              negative values rotate clockwise
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
   * @param degrees angle or rotation in degrees, positive values rotate counter-clockwise,
   *                negative values rotate clockwise
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


  //-- query operations
  public static double width(Graphic graphic) {
    return graphic.getWidth();
  }

  public static double height(Graphic graphic) {
    return graphic.getHeight();
  }

}
