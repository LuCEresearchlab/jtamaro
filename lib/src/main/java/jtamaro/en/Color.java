package jtamaro.en;

import jtamaro.internal.representation.ColorImpl;


/**
 * A Color in the RGBA color space.
 *
 * <p>To work with a Color, you can use the methods in the Colors class.
 *
 * @see jtamaro.en.Colors
 */
public final class Color {

  private final ColorImpl implementation;

  private Color(ColorImpl implementation) {
    this.implementation = implementation;
  }

  /**
   * Create a Color with the given components for red (R), green (G), and blue (B),
   * and a certain degree of opacity (alpha, A).
   *
   * @param red     red component [0-255]
   * @param green   green component [0-255]
   * @param blue    blue component [0-255]
   * @param opacity opacity (alpha) of the color, where 0.0 means fully
   *                transparent and 1.0 fully opaque.
   */
  public Color(int red, int green, int blue, double opacity) {
    this(new ColorImpl(red, green, blue, opacity));
  }

  /**
   * Create a Color with the provided hue (H), saturation (S), lightness (L),
   * and a certain degree of opacity (alpha, A).
   *
   * <p><img src="https://upload.wikimedia.org/wikipedia/commons/3/35/HSL_color_solid_cylinder.png" alt="HSV cylinder" width="250px">
   *
   * @param hue        hue of the color [0-360]
   * @param saturation saturation of the color [0-1]
   * @param lightness  the amount of white or black applied [0-1].
   *                   Fully saturated colors have a lightness value of 1/2.
   * @param opacity    opacity (alpha) of the color, where 0.0 means fully
   *                   transparent and 1.0 fully opaque.
   */
  public Color(double hue, double saturation, double lightness, double opacity) {
    this(ColorImpl.fromHSLA(hue, saturation, lightness, opacity));
  }

  /**
   * Returns a Color with the provided hue (H), saturation (S), value (V),
   * and a certain degree of opacity (alpha, A).
   *
   * <p><img src="https://upload.wikimedia.org/wikipedia/commons/4/4e/HSV_color_solid_cylinder.png" alt="HSV cylinder" width="250px">
   *
   * @param hue        hue of the color [0-360]
   * @param saturation saturation of the color [0-1]
   * @param value      the amount of light that is applied [0-1]
   * @param opacity    opacity (alpha) of the color, where 0.0 means fully
   *                   transparent and 1.0 fully opaque.
   * @return a Color with the provided HSVA components.
   */
  public static Color fromHSVA(double hue, double saturation, double value, double opacity) {
    return new Color(ColorImpl.fromHSVA(hue, saturation, value, opacity));
  }

  /**
   * This is an internal method. Please don't use it.
   */
  public ColorImpl getImplementation() {
    return implementation;
  }

  public String toString() {
    return "rgb(" + implementation.getRed() +
        ", " + implementation.getGreen() +
        ", " + implementation.getBlue() +
        ", " + implementation.getOpacity() +
        ")";
  }

}
