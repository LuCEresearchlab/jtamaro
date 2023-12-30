package jtamaro.internal.representation;

// new Point API

/**
 * A "normalized" point in the local coordinate system of a graphic.
 * The bounding box corners have points:
 * (-1, -1), (-1, 1), (1, 1), (1, -1).
 */
public class PointImpl {

  public static final PointImpl TOP_LEFT = new PointImpl(-1, -1);
  public static final PointImpl TOP_MIDDLE = new PointImpl(0, -1);
  public static final PointImpl TOP_RIGHT = new PointImpl(1, -1);
  public static final PointImpl MIDDLE_LEFT = new PointImpl(-1, 0);
  public static final PointImpl MIDDLE = new PointImpl(0, 0);
  public static final PointImpl MIDDLE_RIGHT = new PointImpl(1, 0);
  public static final PointImpl BOTTOM_LEFT = new PointImpl(-1, 1);
  public static final PointImpl BOTTOM_MIDDLE = new PointImpl(0, 1);
  public static final PointImpl BOTTOM_RIGHT = new PointImpl(1, 1);

  private final double x;
  private final double y;

  public PointImpl(final double x, final double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  @Override
  public String toString() {
    return "PointImpl[x=" + x + ", y=" + y + "]";
  }

}
