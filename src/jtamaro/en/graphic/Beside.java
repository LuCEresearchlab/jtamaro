package jtamaro.en.graphic;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.BesideImpl;


public final class Beside extends AbstractGraphic {

  public Beside(Graphic leftGraphic, Graphic rightGraphic) {
    super(new BesideImpl(
        ((AbstractGraphic) leftGraphic).getImplementation(),
        ((AbstractGraphic) rightGraphic).getImplementation()));
  }

}
