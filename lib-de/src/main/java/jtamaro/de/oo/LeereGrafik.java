package jtamaro.de.oo;

import jtamaro.internal.representation.EmptyGraphicImpl;


public class LeereGrafik extends AbstrakteGrafik {

  public LeereGrafik() {
    super(new EmptyGraphicImpl());
  }

}
