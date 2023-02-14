package jtamaro.de.oo;

import jtamaro.de.Farbe;
import jtamaro.internal.representation.TriangleImpl;


public final class Dreieck extends AbstrakteGrafik {

  public Dreieck(double seite1, double seite2, double winkel, Farbe farbe) {
    super(new TriangleImpl(seite1, seite2, winkel, farbe.getImplementation()));
  }

}
