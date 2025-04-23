package jtamaro.graphic;

import java.util.Map;

/**
 * Names of notable points, that can be used as pinning positions for a graphic.
 *
 * @see jtamaro.graphic.Point
 */
public final class Points {

  /**
   * The top left corner of the bounding box.
   */
  public static final Point TOP_LEFT = new Point(-1, -1);

  /**
   * The middle point of the top edge of the bounding box.
   */
  public static final Point TOP_CENTER = new Point(0, -1);

  /**
   * The top right corner of the bounding box.
   */
  public static final Point TOP_RIGHT = new Point(1, -1);

  /**
   * The middle point of the left edge of the bounding box.
   */
  public static final Point CENTER_LEFT = new Point(-1, 0);

  /**
   * The center point of the bounding box.
   */
  public static final Point CENTER = new Point(0, 0);

  /**
   * The middle point of the right edge of the bounding box.
   */
  public static final Point CENTER_RIGHT = new Point(1, 0);

  /**
   * The bottom left corner of the bounding box.
   */
  public static final Point BOTTOM_LEFT = new Point(-1, 1);

  /**
   * The middle point of the bottom edge of the bounding box.
   */
  public static final Point BOTTOM_CENTER = new Point(0, 1);

  /**
   * The bottom right corner of the bounding box.
   */
  public static final Point BOTTOM_RIGHT = new Point(1, 1);

  private static final Map<Point, String> KNOWN_POINT_NAMES = Map.of(
      TOP_LEFT, "TOP_LEFT",
      TOP_CENTER, "TOP_CENTER",
      TOP_RIGHT, "TOP_RIGHT",
      CENTER_LEFT, "CENTER_LEFT",
      CENTER, "CENTER",
      CENTER_RIGHT, "CENTER_RIGHT",
      BOTTOM_LEFT, "BOTTOM_LEFT",
      BOTTOM_CENTER, "BOTTOM_CENTER",
      BOTTOM_RIGHT, "BOTTOM_RIGHT"
  );

  /**
   * Format point as a string using known point names, if possible.
   */
  static String format(Point point) {
    return KNOWN_POINT_NAMES.containsKey(point)
        ? KNOWN_POINT_NAMES.get(point)
        : point.toString();
  }

  private Points() {
  }
}
