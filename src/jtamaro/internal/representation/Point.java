package jtamaro.internal.representation;


/**
 * A point corresponds to a place on a specific graphic.
 */
public class Point {

  private final GraphicImpl graphic;
  private final Place place;
  private final double x;
  private final double y;

  public Point(final GraphicImpl graphic, final Place place, final double x, final double y) {
    this.graphic = graphic;
    this.place = place;
    this.x = x;
    this.y = y;
  }

  GraphicImpl getGraphic() {
    return graphic;
  }

  double getX() {
    return x;
  }

  double getY() {
    return y;
  }

  public String toString() {
    return "Point[graphic=" + graphic + ", place=" + place +"]";
  }

}
