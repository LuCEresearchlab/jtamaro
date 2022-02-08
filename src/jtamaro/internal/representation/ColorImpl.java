package jtamaro.internal.representation;

import java.util.function.Function;

public final class ColorImpl {

  private final int red;
  private final int green;
  private final int blue;
  private final int alpha;


  public ColorImpl(int red, int green, int blue, int alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  public static ColorImpl fromHSLA(double hue, double saturation, double lightness, int alpha) {
    // convert from RGB to HSL
    // https://en.wikipedia.org/wiki/HSL_and_HSV#HSL_to_RGB
    final double H = hue; // [0, 360]
    final double S = saturation; // [0,1]
    final double L = lightness; // [0,1]
    final Function<Integer,Double> f = (Integer n) -> {
      final double k = (n + H / 30) % 12;
      final double a = S * Math.min(L, 1-L);
      return L - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
    };
    final int red = (int)Math.floor(f.apply(0) * 255);
    final int green = (int)Math.floor(f.apply(8) * 255);
    final int blue = (int)Math.floor(f.apply(4) * 255);
    return new ColorImpl(red, green, blue, alpha);
  }

  public java.awt.Color toAWT() {
    return new java.awt.Color(red, green, blue, alpha);
  }

  @Override
  public String toString() {
    return "rgba(" + red + ", " + green + ", " + blue + ", " + alpha +")";
  }

}
