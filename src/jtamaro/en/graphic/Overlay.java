package jtamaro.en.graphic;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.OverlayImpl;


public final class Overlay extends AbstractGraphic {

  public Overlay(Graphic foregroundGraphic, Graphic backgroundGraphic) {
    super(new OverlayImpl(
      ((AbstractGraphic)foregroundGraphic).getImplementation(),
      ((AbstractGraphic)backgroundGraphic).getImplementation()));
  }

}
