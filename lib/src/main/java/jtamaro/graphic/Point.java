package jtamaro.graphic;

import java.util.Objects;

/**
 * A Point in a Graphic. There are some noteworthy points, like the top-left corner of a graphic's
 * bounding box. For those points class Points provides constants ({@code TOP_LEFT},
 * {@code TOP_RIGHT}, etc.).
 *
 * <p>The points are "normalized" point in the local coordinate system of a graphic. The bounding
 * box corners have points: {@code (-1, -1), (-1, 1), (1, 1), (1, -1)}.
 *
 * @see jtamaro.graphic.Points
 */
public final class Point {

  private final int x;

  private final int y;

  /* package */ Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (other instanceof Point that) {
      return that.x == x
          && that.y == y;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "Point[x=" + x + ", y=" + y + ']';
  }
}
