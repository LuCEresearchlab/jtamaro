package jtamaro.de.oo;

import jtamaro.de.Grafik;
import jtamaro.internal.representation.GraphicImpl;


public abstract class AbstrakteGrafik implements Grafik {

  private final GraphicImpl implementation;

  public AbstrakteGrafik(GraphicImpl implementation) {
    this.implementation = implementation;
  }

  GraphicImpl getImplementation() {
    return implementation;
  }
  
}
