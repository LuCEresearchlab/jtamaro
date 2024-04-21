package jtamaro.en.graphic;

import jtamaro.en.Graphic;
import jtamaro.internal.representation.NameImpl;


public final class Name extends AbstractGraphic {

  public Name(String name, Graphic graphic) {
    super(new NameImpl(name, ((AbstractGraphic) graphic).getImplementation()));
  }

}
