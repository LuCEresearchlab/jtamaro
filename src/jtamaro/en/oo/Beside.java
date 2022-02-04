package jtamaro.en.oo;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.OverlayImpl;


public final class Beside extends AbstractGraphic {

  public Beside(Graphic leftGraphic, Graphic rightGraphic) {
    super(new OverlayImpl(
      ((AbstractGraphic)leftGraphic).getImplementation(),
      ((AbstractGraphic)rightGraphic).getImplementation()));
  }

}
