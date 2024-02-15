package jtamaro.de.oo;

import jtamaro.de.Farbe;
import jtamaro.internal.representation.CircularSectorImpl;


public final class KreisSektor extends AbstrakteGrafik {

  public KreisSektor(double radius, double winkel, Farbe farbe) {
    super(new CircularSectorImpl(radius, winkel, farbe.getImplementation()));
  }

}
