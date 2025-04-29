package jtamaro.graphic;

/**
 * Static methods for working with graphics.
 *
 * @see jtamaro.graphic.Graphic
 */
public final class Graphics {

  private Graphics() {
  }

  /* **** Primitive shapes **** */

  /**
   * Creates a circular sector belonging to a circle of the given radius, filled with a color.
   *
   * <p>A circular sector is a portion of a circle enclosed between two radii and an arc.
   * Considering a circle as a clock, the first radius is supposed to "point" towards 3 o’clock. The
   * angle determines the position of the second radius, computed starting from the first one in
   * counterclockwise direction. An angle of 360 degrees corresponds to a full circle.
   *
   * <p>The pinning position is at the center of the circle from which the circular sector is
   * taken.
   *
   * @param radius radius of the circle from which the circular sector is taken
   * @param angle  central angle, in degrees
   * @param color  the color to be used to fill the circular sector
   */
  public static Graphic circularSector(double radius, double angle, Color color) {
    return new CircularSector(radius, angle, color);
  }

  /**
   * Creates an ellipse with the given width and height, filled with a color.
   *
   * <p>When width and height are the same, the ellipse becomes a circle with a diameter equal to
   * the provided size.
   *
   * @param width  width of the ellipse
   * @param height height of the ellipse
   * @param color  color to be used to fill the ellipse
   */
  public static Graphic ellipse(double width, double height, Color color) {
    return new Ellipse(width, height, color);
  }

  /**
   * Creates an empty graphic. When an empty graphic is composed with any other graphic, it behaves
   * as a neutral element: the result is always identical to the other graphic.
   */
  public static Graphic emptyGraphic() {
    return new EmptyGraphic();
  }

  /**
   * Creates a rectangle of the given size, filled with a color.
   *
   * @param width  width of the rectangle
   * @param height height of the rectangle
   * @param color  color to be used to fill the rectangle
   */
  public static Graphic rectangle(double width, double height, Color color) {
    return new Rectangle(width, height, color);
  }

  /**
   * Creates a graphic with the text rendered using the specified font, size and color.
   *
   * <p>>When the indicated True-Type Font is not found in the system, a very basilar font that is
   * always available is used instead. The resulting graphic has the minimal size that still fits
   * the whole text.
   *
   * <p>The pinning position is horizontally aligned on the left and vertically on the baseline of
   * the text.
   *
   * @param content text to render
   * @param font    the font to be used to render the text
   * @param size    size in typographic points (e.g., 16)
   * @param color   color to be used to fill the text
   */
  public static Graphic text(String content, String font, double size, Color color) {
    return new Text(content, font, size, color);
  }

  /**
   * Creates a triangle specifying two sides and the angle between them, filled with a color. The
   * first side extends horizontally to the right. The angle specifies how much the second side is
   * rotated, counterclockwise, from the first one.
   *
   * <p>For all triangles, except obtuse ones, the bottom-left corner of the resulting graphic
   * coincides with the vertex of the triangle for which the angle is specified.
   *
   * <p>The pinning position is the centroid of the triangle.
   *
   * @param side1 length of the first, horizontal side of the triangle
   * @param side2 length of the second side of the triangle
   * @param angle angle between the two sides, in degrees
   * @param color color to be used to fill the triangle
   */
  public static Graphic triangle(double side1, double side2, double angle, Color color) {
    return new Triangle(side1, side2, angle, color);
  }

  /* **** Operations **** */

  /**
   * Creates a new graphic by placing the two graphics one above the other. The two graphics are
   * horizontally centered.
   *
   * <p>The pinning position of the new graphic is at its center.
   *
   * @param top    graphic to place on the top
   * @param bottom graphic to place on the bottom
   */
  public static Graphic above(Graphic top, Graphic bottom) {
    return new Above(top, bottom);
  }

  /**
   * Creates a new graphic by placing the two graphics one besides the other. The two graphics are
   * vertically centered.
   *
   * <p>The pinning position of the new graphic is at its center.
   *
   * @param left  graphic to place on the left
   * @param right graphic to place on the right
   */
  public static Graphic beside(Graphic left, Graphic right) {
    return new Beside(left, right);
  }

  /**
   * Creates a new graphic by composing the two provided graphics. The first graphic is kept in the
   * foreground, the second one in the background. The graphics are aligned by superimposing their
   * pinning positions.
   *
   * <p>The pinning position used to compose becomes the pinning position of the resulting graphic.
   *
   * @param foreground graphic in the foreground
   * @param background graphic in the background
   */
  public static Graphic compose(Graphic foreground, Graphic background) {
    return new Compose(foreground, background);
  }

  /**
   * Creates a new graphic by overlaying the two provided graphics, keeping the first one in the
   * foreground and the second one in background. The two graphics are overlaid on their centers.
   *
   * <p>The pinning position of the new graphic is at its center.
   *
   * @param foreground graphic in the foreground
   * @param background graphic in the background
   */
  public static Graphic overlay(Graphic foreground, Graphic background) {
    return new Overlay(foreground, background);
  }

  /**
   * Creates a new graphic that corresponds to the provided graphic, with a new pinning position.
   *
   * <p>Each graphic is contained in a rectangular bounding box. There are 9 notable points,
   * corresponding to the four corners of this rectangle, the middle points of the four edges and
   * the center of the rectangle. These points can be referred to using these names:
   * <code>TOP_LEFT</code>, <code>TOP_CENTER</code>, <code>TOP_RIGHT</code>,
   * <code>CENTER_LEFT</code>, <code>CENTER</code>, <code>CENTER_RIGHT</code>,
   * <code>BOTTOM_LEFT</code>, <code>BOTTOM_CENTER</code> and <code>BOTTOM_RIGHT</code>.
   *
   * @param pinPoint the point indicating the new pinning position
   * @param graphic  original graphic
   * @see Points#TOP_LEFT
   * @see Points#TOP_CENTER
   * @see Points#TOP_RIGHT
   * @see Points#CENTER_LEFT
   * @see Points#CENTER
   * @see Points#CENTER_RIGHT
   * @see Points#BOTTOM_LEFT
   * @see Points#BOTTOM_CENTER
   * @see Points#BOTTOM_RIGHT
   */
  public static Graphic pin(Point pinPoint, Graphic graphic) {
    return new Pin(pinPoint, graphic);
  }

  /**
   * Creates a new graphic by rotating counterclockwise the provided graphic around its pinning
   * position by the given angle. A negative angle corresponds to a clockwise rotation.
   *
   * @param angle   angle of counterclockwise rotation, in degrees
   * @param graphic graphic to rotate
   */
  public static Graphic rotate(double angle, Graphic graphic) {
    return new Rotate(angle, graphic);
  }
}
