package jtamaro.en.graphic;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.ComposeImpl;


public final class Compose extends AbstractGraphic {

  public Compose(Graphic foregroundGraphic, Graphic backgroundGraphic) {
    super(new ComposeImpl(
      ((AbstractGraphic)foregroundGraphic).getImplementation(),
      ((AbstractGraphic)backgroundGraphic).getImplementation()));
  }

}
