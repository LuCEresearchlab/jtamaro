package jtamaro.graphic;

import jtamaro.interaction.Coordinate;

/**
 * Location with coordinates relative to a given graphic.
 *
 * @hidden
 */
record RelativeLocation(
    Graphic graphic,
    double x,
    double y
) {

  /**
   * Check whether the location is relative to a given graphic.
   *
   * @implNote This check is done on identity, not equality!
   */
  public boolean isOfGraphic(Graphic graphic) {
    return this.graphic == graphic;
  }

  public Coordinate relativeCoordinates() {
    return new Coordinate((int) x, (int) y);
  }
}
