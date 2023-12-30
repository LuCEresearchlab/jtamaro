package jtamaro.en.graphic;

import jtamaro.en.Color;
import jtamaro.internal.representation.EquilateralTriangleImpl;


// OLD
public final class EquilateralTriangle extends AbstractGraphic {

  public EquilateralTriangle(double side, Color color) {
    super(new EquilateralTriangleImpl(side, color.getImplementation()));
  }

}
