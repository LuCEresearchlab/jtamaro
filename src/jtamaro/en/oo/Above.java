package jtamaro.en.oo;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.AboveImpl;


public final class Above extends AbstractGraphic {

  public Above(Graphic topGraphic, Graphic bottomGraphic) {
    super(new AboveImpl(
      ((AbstractGraphic)topGraphic).getImplementation(),
      ((AbstractGraphic)bottomGraphic).getImplementation()));
  }

}
