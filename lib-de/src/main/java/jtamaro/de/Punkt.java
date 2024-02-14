package jtamaro.de;

import jtamaro.internal.representation.PointImpl;

public final class Punkt {

  public static final Punkt OBEN_LINKS = new Punkt(PointImpl.TOP_LEFT);
  public static final Punkt OBEN_MITTE = new Punkt(PointImpl.TOP_MIDDLE);
  public static final Punkt OBEN_RECHTS = new Punkt(PointImpl.TOP_RIGHT);
  public static final Punkt MITTE_LINKS = new Punkt(PointImpl.MIDDLE_LEFT);
  public static final Punkt MITTE = new Punkt(PointImpl.MIDDLE);
  public static final Punkt MITTE_RECHTS = new Punkt(PointImpl.MIDDLE_RIGHT);
  public static final Punkt UNTEN_LINKS = new Punkt(PointImpl.BOTTOM_LEFT);
  public static final Punkt UNTEN_MITTE = new Punkt(PointImpl.BOTTOM_MIDDLE);
  public static final Punkt UNTEN_RECHTS = new Punkt(PointImpl.BOTTOM_RIGHT);


  private final PointImpl implementation;

  private Punkt(PointImpl implementation) {
    this.implementation = implementation;
  }

  /**
   * Dies ist eine interne Methode. Bitte nicht verwenden.
   */
  public PointImpl getImplementation() {
    return implementation;
  }

}
