package jtamaro.de.oo;

import jtamaro.de.Grafik;
import jtamaro.de.Punkt;
import jtamaro.internal.representation.PinPointImpl;


public final class Fixiere extends AbstrakteGrafik {

  public Fixiere(Punkt punkt, Grafik grafik) {
    super(new PinPointImpl(punkt.getImplementation(), ((AbstrakteGrafik)grafik).getImplementation()));
  }

}
