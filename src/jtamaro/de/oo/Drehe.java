package jtamaro.de.oo;

import jtamaro.de.Grafik;
import jtamaro.internal.representation.RotateImpl;


public final class Drehe extends AbstrakteGrafik {

  public Drehe(double grad, Grafik grafik) {
    super(new RotateImpl(grad, ((AbstrakteGrafik)grafik).getImplementation()));
  }

}
