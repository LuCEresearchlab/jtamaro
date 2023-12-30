package jtamaro.en.graphic;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.RotateImpl;


public final class Rotate extends AbstractGraphic {

  public Rotate(double degrees, Graphic graphic) {
    super(new RotateImpl(degrees, ((AbstractGraphic) graphic).getImplementation()));
  }

}
