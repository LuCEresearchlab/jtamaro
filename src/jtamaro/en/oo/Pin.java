package jtamaro.en.oo;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.PinImpl;


public final class Pin extends AbstractGraphic {

  public Pin(String horizontalPlace, String verticalPlace, Graphic graphic) {
    super(new PinImpl(horizontalPlace, verticalPlace, ((AbstractGraphic)graphic).getImplementation()));
  }

}
