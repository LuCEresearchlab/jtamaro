package jtamaro.de.oo;

import jtamaro.de.Grafik;
import jtamaro.internal.representation.ComposeImpl;


public final class Kombiniere extends AbstrakteGrafik {

  public Kombiniere(Grafik vordereGrafik, Grafik hintereGrafik) {
    super(new ComposeImpl(
        ((AbstrakteGrafik) vordereGrafik).getImplementation(),
        ((AbstrakteGrafik) hintereGrafik).getImplementation()));
  }

}
