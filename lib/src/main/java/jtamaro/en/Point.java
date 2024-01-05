package jtamaro.en;

import jtamaro.internal.representation.PointImpl;


/**
 * A Point in a Graphic.
 * There are some noteworthy points,
 * like the top-left corner of a graphic's bounding box.
 * For those points class Points provides constants (TOP_LEFT, TOP_MIDDLE, etc.).
 * 
 * @see jtamaro.en.Points
 */
public final class Point {

  static final Point TOP_LEFT = new Point(PointImpl.TOP_LEFT);
  static final Point TOP_CENTER = new Point(PointImpl.TOP_MIDDLE);
  static final Point TOP_RIGHT = new Point(PointImpl.TOP_RIGHT);
  static final Point CENTER_LEFT = new Point(PointImpl.MIDDLE_LEFT);
  static final Point CENTER = new Point(PointImpl.MIDDLE);
  static final Point CENTER_RIGHT = new Point(PointImpl.MIDDLE_RIGHT);
  static final Point BOTTOM_LEFT = new Point(PointImpl.BOTTOM_LEFT);
  static final Point BOTTOM_CENTER = new Point(PointImpl.BOTTOM_MIDDLE);
  static final Point BOTTOM_RIGHT = new Point(PointImpl.BOTTOM_RIGHT);


  private final PointImpl implementation;

  private Point(PointImpl implementation) {
    this.implementation = implementation;
  }

  /**
   * This is an internal method. Please don't use it.
   */
  public PointImpl getImplementation() {
    return implementation;
  }

}
