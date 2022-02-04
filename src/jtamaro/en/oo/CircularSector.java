package jtamaro.en.oo;

import jtamaro.en.Color;
import jtamaro.internal.representation.CircularSectorImpl;


public final class CircularSector extends AbstractGraphic {

  public CircularSector(double radius, double angle, Color color) {
    super(new CircularSectorImpl(radius, angle, color.getImplementation()));
  }

}
