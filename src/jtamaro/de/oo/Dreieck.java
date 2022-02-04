package jtamaro.de.oo;

import jtamaro.de.Farbe;
import jtamaro.internal.representation.TriangleImpl;


public final class Dreieck extends AbstrakteGrafik {

  public Dreieck(double seite, Farbe farbe) {
    super(new TriangleImpl(seite, farbe.getImplementation()));
  }

}
