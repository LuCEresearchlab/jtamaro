package jtamaro.de;

import jtamaro.internal.representation.ColorImpl;


/**
 * Repräsentiert eine Farbe.
 * Eine Farbe hat auch eine gewisse Durchsichtigkeit,
 * von komplett undurchsichtig, wie die Farbe ROT,
 * bis komplett durchsichtig, wie die Farbe TRANSPARENT.
 */
public final class Farbe {

  private final ColorImpl implementation;

  private Farbe(ColorImpl implementation) {
    this.implementation = implementation;
  }

  /**
   * Dies ist eine interne Methode. Bitte nicht verwenden.
   */
  public ColorImpl getImplementation() {
    return implementation;
  }

  //-- well-known colors
  public static final Farbe SCHWARZ = rgb(0, 0, 0);
  public static final Farbe WEISS = rgb(255, 255, 255);
  public static final Farbe ROT = rgb(255, 0, 0);
  public static final Farbe GRUEN = rgb(0, 255, 0);
  public static final Farbe BLAU = rgb(0, 0, 255);
  public static final Farbe CYAN = rgb(0, 255, 255);
  public static final Farbe MAGENTA = rgb(255, 0, 255);
  public static final Farbe GELB = rgb(255, 255, 0);
  public static final Farbe TRANSPARENT = rgba(0, 0, 0, 0);

  public static Farbe rgb(int rot, int gruen, int blau) {
    return rgba(rot, gruen, blau, 255);
  }

  public static Farbe rgba(int rot, int gruen, int blau, int alpha) {
    return new Farbe(new ColorImpl(rot, gruen, blau, alpha));
  }
  
  public static Farbe hsl(double farbwinkel, double saettigung, double helligkeit) {
    return hsla(farbwinkel, saettigung, helligkeit, 255);
  }

  public static Farbe hsla(double farbwinkel, double saettigung, double helligkeit, int alpha) {
    return new Farbe(ColorImpl.fromHSLA(farbwinkel, saettigung, helligkeit, alpha));
  }

}
