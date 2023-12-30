package jtamaro.internal.representation;


/**
 * A location corresponds to a place on a specific graphic.
 */
public class Location {

  private final GraphicImpl graphic;
  // coordinates in the graphic's coordinate system:
  private final double x;
  private final double y;

  public Location(final GraphicImpl graphic, final double x, final double y) {
    this.graphic = graphic;
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

  @Override
  public String toString() {
    return "Location[graphic=" + graphic + ", x=" + x + ", y=" + y + "]";
  }

}
