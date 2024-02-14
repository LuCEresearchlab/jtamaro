package jtamaro.de.oo;

import jtamaro.de.Grafik;
import jtamaro.internal.representation.GraphicImpl;


public abstract class AbstrakteGrafik implements Grafik {

  private final GraphicImpl implementation;

  public AbstrakteGrafik(GraphicImpl implementation) {
    this.implementation = implementation;
  }

  public GraphicImpl getImplementation() {
    return implementation;
  }

  @Override
  public double gibBreite() {
    return implementation.getWidth();
  }

  @Override
  public double gibHoehe() {
    return implementation.getHeight();
  }

}
