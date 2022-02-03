package jtamaro.en.oo;

import jtamaro.internal.representation.EmptyGraphicImpl;


public class EmptyGraphic extends AbstractGraphic {

  public EmptyGraphic() {
    super(new EmptyGraphicImpl());
  }

}
