package jtamaro.de.oo;

import jtamaro.de.Farbe;
import jtamaro.internal.representation.RectangleImpl;


public final class Rechteck extends AbstrakteGrafik {

  public Rechteck(double breite, double hoehe, Farbe farbe) {
    super(new RectangleImpl(breite, hoehe, farbe.getImplementation()));
  }

}
