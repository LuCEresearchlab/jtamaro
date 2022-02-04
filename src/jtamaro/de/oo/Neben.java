package jtamaro.de.oo;

import jtamaro.de.Grafik;
import jtamaro.internal.representation.OverlayImpl;


public final class Neben extends AbstrakteGrafik {

  public Neben(Grafik linkeGrafik, Grafik rechteGrafik) {
    super(new OverlayImpl(
      ((AbstrakteGrafik)linkeGrafik).getImplementation(),
      ((AbstrakteGrafik)rechteGrafik).getImplementation()));
  }

}
