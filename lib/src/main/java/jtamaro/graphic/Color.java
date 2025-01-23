package jtamaro.graphic;

/**
 * Represents a color. A color also has a degree of opacity, from completely transparent (like the
 * color transparent) to completely opaque (like the color red).
 *
 * @param red     red component [0-255]
 * @param green   green component [0-255]
 * @param blue    blue component [0-255]
 * @param opacity opacity (alpha) of the color, where 0 means fully transparent and 1 fully opaque.
 *                By default, all colors are fully opaque
 */
public record Color(
    int red,
    int green,
    int blue,
    double opacity
) {

  public Color {
    assert red >= 0x00 && red <= 0xff;
    assert green >= 0x00 && green <= 0xff;
    assert blue >= 0x00 && blue <= 0xff;
    assert opacity >= 0.0 && opacity <= 1.0;
  }

  public int alpha() {
    // TODO: Conversion between [0.0, 1.0] and [0, 255] is not satisfactory
    return (int) Math.floor(opacity * 255.0);
  }

  @Override
  public String toString() {
    return String.format("Color[rgb=#%1$02X%2$02X%3$02X, opacity=%4$.2f]", red, green, blue, opacity);
  }
}
