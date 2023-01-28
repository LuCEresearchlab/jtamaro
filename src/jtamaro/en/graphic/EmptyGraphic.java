package jtamaro.en.graphic;

import jtamaro.internal.representation.EmptyGraphicImpl;


public class EmptyGraphic extends AbstractGraphic {

  public EmptyGraphic() {
    super(new EmptyGraphicImpl());
  }

}
