package jtamaro.de.oo;

import jtamaro.de.Grafik;
import jtamaro.internal.representation.AboveImpl;


public final class Ueber extends AbstrakteGrafik {

  public Ueber(Grafik obereGrafik, Grafik untereGrafik) {
    super(new AboveImpl(
        ((AbstrakteGrafik) obereGrafik).getImplementation(),
        ((AbstrakteGrafik) untereGrafik).getImplementation()));
  }

}
