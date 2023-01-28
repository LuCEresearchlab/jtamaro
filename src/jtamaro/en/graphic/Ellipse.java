package jtamaro.en.graphic;

import jtamaro.en.Color;
import jtamaro.internal.representation.EllipseImpl;


public final class Ellipse extends AbstractGraphic {

  public Ellipse(double width, double height, Color color) {
    super(new EllipseImpl(width, height, color.getImplementation()));
  }

}
