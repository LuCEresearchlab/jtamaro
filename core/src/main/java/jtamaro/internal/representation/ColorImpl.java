package jtamaro.internal.representation;

import java.util.function.Function;

public final class ColorImpl {

  private final int red;
  private final int green;
  private final int blue;
  private final int alpha;


  public ColorImpl(int red, int green, int blue, double opacity) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    //TODO: Conversion between [0.0, 1.0] and [0, 255] is not satisfactory
    this.alpha = (int) Math.floor(opacity * 255);
  }

  public static ColorImpl fromHSLA(double hue, double saturation, double lightness, double opacity) {
    // convert from RGB to HSL
    // https://en.wikipedia.org/wiki/HSL_and_HSV#HSL_to_RGB
    final double H = hue; // [0, 360]
    final double S = saturation; // [0,1]
    final double L = lightness; // [0,1]
    final Function<Integer, Double> f = (Integer n) -> {
      final double k = (n + H / 30) % 12;
      final double a = S * Math.min(L, 1 - L);
      return L - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
    };
    final int red = (int) Math.floor(f.apply(0) * 255);
    final int green = (int) Math.floor(f.apply(8) * 255);
    final int blue = (int) Math.floor(f.apply(4) * 255);
    return new ColorImpl(red, green, blue, opacity);
  }

  public static ColorImpl fromHSVA(double hue, double saturation, double value, double opacity) {
    // convert from RGB to HSL
    // https://en.wikipedia.org/wiki/HSL_and_HSV#HSV_to_RGB
    final double H = hue; // [0, 360]
    final double S = saturation; // [0,1]
    final double V = value; // [0,1]
    final Function<Integer, Double> f = (Integer n) -> {
      final double k = (n + H / 60) % 6;
      return V - V * S * Math.max(0, Math.min(k, Math.min(4 - k, 1)));
    };
    final int red = (int) Math.floor(f.apply(5) * 255);
    final int green = (int) Math.floor(f.apply(3) * 255);
    final int blue = (int) Math.floor(f.apply(1) * 255);
    return new ColorImpl(red, green, blue, opacity);
  }

  public java.awt.Color toAWT() {
    return new java.awt.Color(red, green, blue, alpha);
  }

  public int getRed() {
    return red;
  }

  public int getGreen() {
    return green;
  }

  public int getBlue() {
    return blue;
  }

  /**
   * Returns the opacity in the range [0, 255],
   * like it is stored.
   * 
   * @return the opacity as a value in [0, 255].
   */
  public int getAlpha() {
    return alpha;
  }

  /**
   * Returns the opacity in the range [0.0, 1.0],
   * as it is provided when creating a color.
   * @return the opacity as a value in [0.0, 1.0].
   */
  public double getOpacity() {
    //TODO: Conversion between [0.0, 1.0] and [0, 255] is not satisfactory
    return alpha / 255.0;
  }
  
  /**
   * This shows the RGBA components as they are represented internally.
   */
  @Override
  public String toString() {
    return "rgba(" + red + ", " + green + ", " + blue + ", " + alpha + ")";
  }

}
