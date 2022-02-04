package jtamaro.de.oo;

import jtamaro.de.Grafik;
import jtamaro.internal.representation.PinImpl;


public final class Fixiere extends AbstrakteGrafik {

  public Fixiere(String horizontalePosition, String vertikalePosition, Grafik grafik) {
    super(new PinImpl(horizontalePosition, vertikalePosition, ((AbstrakteGrafik)grafik).getImplementation()));
  }

}
