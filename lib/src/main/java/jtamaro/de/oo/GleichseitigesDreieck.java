package jtamaro.de.oo;

import jtamaro.de.Farbe;
import jtamaro.internal.representation.EquilateralTriangleImpl;


//OLD
public final class GleichseitigesDreieck extends AbstrakteGrafik {

  public GleichseitigesDreieck(double seite, Farbe farbe) {
    super(new EquilateralTriangleImpl(seite, farbe.getImplementation()));
  }

}
