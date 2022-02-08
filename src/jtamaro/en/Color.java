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

  /**
   * This is an internal method. Please don't use it.
   */
  public ColorImpl getImplementation() {
    return implementation;
  }

  //-- well-known colors
  public static final Color BLACK = rgb(0, 0, 0);
  public static final Color WHITE = rgb(255, 255, 255);
  public static final Color RED = rgb(255, 0, 0);
  public static final Color GREEN = rgb(0, 255, 0);
  public static final Color BLUE = rgb(0, 0, 255);
  public static final Color CYAN = rgb(0, 255, 255);
  public static final Color MAGENTA = rgb(255, 0, 255);
  public static final Color YELLOW = rgb(255, 255, 0);
  public static final Color TRANSPARENT = rgba(0, 0, 0, 0);

  public static Color rgb(int rot, int gruen, int blau) {
    return rgba(rot, gruen, blau, 255);
  }

  public static Color rgba(int rot, int gruen, int blau, int alpha) {
    return new Color(new ColorImpl(rot, gruen, blau, alpha));
  }
  
  public static Color hsl(double hue, double saturation, double lightness) {
    return hsla(hue, saturation, lightness, 255);
  }

  public static Color hsla(double hue, double saturation, double lightness, int alpha) {
    return new Color(ColorImpl.fromHSLA(hue, saturation, lightness, alpha));
  }
  
}
