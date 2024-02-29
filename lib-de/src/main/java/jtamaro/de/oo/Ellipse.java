package jtamaro.de.oo;

import jtamaro.de.Farbe;
import jtamaro.internal.representation.EllipseImpl;


public final class Ellipse extends AbstrakteGrafik {

  public Ellipse(double breite, double hoehe, Farbe farbe) {
    super(new EllipseImpl(breite, hoehe, farbe.getImplementation()));
  }

}
