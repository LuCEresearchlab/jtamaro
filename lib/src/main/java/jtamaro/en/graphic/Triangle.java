package jtamaro.en.graphic;

import jtamaro.en.Color;
import jtamaro.internal.representation.TriangleImpl;


public final class Triangle extends AbstractGraphic {

  public Triangle(double side1, double side2, double angle, Color color) {
    super(new TriangleImpl(side1, side2, angle, color.getImplementation()));
  }

}
