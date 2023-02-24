package jtamaro.en.graphic;

import jtamaro.en.Graphic;
import jtamaro.en.Point;
import jtamaro.internal.representation.PinPointImpl;


public final class Pin extends AbstractGraphic {

  public Pin(Point point, Graphic graphic) {
    super(new PinPointImpl(point.getImplementation(), ((AbstractGraphic)graphic).getImplementation()));
  }

}
