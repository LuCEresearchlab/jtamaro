package jtamaro.internal.representation;


/**
 * A location corresponds to a place on a specific graphic.
 */
public class Location {

  private final GraphicImpl graphic;
  private final Place place;
  private final double x;
  private final double y;

  public Location(final GraphicImpl graphic, final Place place, final double x, final double y) {
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
    return "Location[graphic=" + graphic + ", place=" + place +"]";
  }

}
