package jtamaro.de.oo;

import jtamaro.de.Grafik;
import jtamaro.internal.representation.OverlayImpl;


public final class Ueberlagere extends AbstrakteGrafik {

  public Ueberlagere(Grafik vordereGrafik, Grafik hintereGrafik) {
    super(new OverlayImpl(
      ((AbstrakteGrafik)vordereGrafik).getImplementation(),
      ((AbstrakteGrafik)hintereGrafik).getImplementation()));
  }

}
