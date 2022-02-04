package jtamaro.en.oo;

import jtamaro.en.Color;
import jtamaro.internal.representation.TriangleImpl;


public final class Triangle extends AbstractGraphic {

  public Triangle(double side, Color color) {
    super(new TriangleImpl(side, color.getImplementation()));
  }

}
