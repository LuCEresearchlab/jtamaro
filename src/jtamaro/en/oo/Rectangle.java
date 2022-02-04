package jtamaro.en.oo;

import jtamaro.en.Color;
import jtamaro.internal.representation.RectangleImpl;


public final class Rectangle extends AbstractGraphic {

  public Rectangle(double width, double height, Color color) {
    super(new RectangleImpl(width, height, color.getImplementation()));
  }

}
