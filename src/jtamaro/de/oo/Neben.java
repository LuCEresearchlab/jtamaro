package jtamaro.de.oo;

import jtamaro.de.Grafik;
import jtamaro.internal.representation.BesideImpl;


public final class Neben extends AbstrakteGrafik {

  public Neben(Grafik linkeGrafik, Grafik rechteGrafik) {
    super(new BesideImpl(
      ((AbstrakteGrafik)linkeGrafik).getImplementation(),
      ((AbstrakteGrafik)rechteGrafik).getImplementation()));
  }

}
