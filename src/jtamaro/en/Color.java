package jtamaro.en;

import jtamaro.internal.representation.ColorImpl;


/**
 * Represents a Color.
 * A Color also has a certain transparency,
 * from completely opaque, like the Color RED,
 * to completely transparent, like the Color TRANSPARENT.
 */
public final class Color {
  
  private final ColorImpl implementation;

  private Color(ColorImpl implementation) {
    this.implementation = implementation;
  }

  public Color(int rot, int gruen, int blau, double opacity) {
    this(new ColorImpl(rot, gruen, blau, opacity));
  }

  public Color(double hue, double saturation, double lightness, double opacity) {
    this(ColorImpl.fromHSLA(hue, saturation, lightness, opacity));
  }

  public static Color fromHSVA(double hue, double saturation, double value, double opacity) {
    return new Color(ColorImpl.fromHSVA(hue, saturation, value, opacity));
  }

  /**
   * This is an internal method. Please don't use it.
   */
  public ColorImpl getImplementation() {
    return implementation;
  }

}
