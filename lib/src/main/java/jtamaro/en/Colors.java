package jtamaro.en;

/**
 * Static methods for working with Colors,
 * and static fields for well-known Colors.
 *
 * @see jtamaro.en.Color
 */
public final class Colors {

  // prevent instantiation
  private Colors() {
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
  public static final Color TRANSPARENT = rgb(0, 0, 0, 0);

  /**
   * Returns a fully opaque Color with the given red (R), green (G), and blue (B) values.
   *
   * @param red   red component [0-255]
   * @param green green component [0-255]
   * @param blue  blue component [0-255]
   * @return a Color with the provided RGB components
   */
  public static Color rgb(int red, int green, int blue) {
    return rgb(red, green, blue, 1.0);
  }

  /**
   * Returns a Color with the given components for red (R), green (G), and blue (B),
   * and a certain degree of opacity (alpha, A).
   *
   * @param red     red component [0-255]
   * @param green   green component [0-255]
   * @param blue    blue component [0-255]
   * @param opacity opacity (alpha) of the color, where 0.0 means fully
   *                transparent and 1.0 fully opaque.
   * @return a Color with the provided RGBA components
   */
  public static Color rgb(int red, int green, int blue, double opacity) {
    assert red >= 0 && red <= 255 : "amount of red must be between 0 and 255";
    assert green >= 0 && green <= 255 : "amount of green must be between 0 and 255";
    assert blue >= 0 && blue <= 255 : "amount of blue must be between 0 and 255";
    assert opacity >= 0 && opacity <= 1 : "opacity (alpha) must be between 0.0 and 1.0";
    return new Color(red, green, blue, opacity);
  }

  /**
   * Returns a fully opaque Color with the provided hue (H), saturation (S), lightness (L).
   * <p>
   * https://upload.wikimedia.org/wikipedia/commons/3/35/HSL_color_solid_cylinder.png
   *
   * @param hue        hue of the color [0-360]
   * @param saturation saturation of the color [0-1]
   * @param lightness  the amount of white or black applied [0-1].
   *                   Fully saturated colors have a lightness value of 1/2.
   * @return a Color with the provided HSL components
   */
  public static Color hsl(double hue, double saturation, double lightness) {
    return hsl(hue, saturation, lightness, 1.0);
  }

  /**
   * Returns a Color with the provided hue (H), saturation (S), lightness (L),
   * and a certain degree of opacity (alpha, A).
   * <p>
   * https://upload.wikimedia.org/wikipedia/commons/3/35/HSL_color_solid_cylinder.png
   *
   * @param hue        hue of the color [0-360]
   * @param saturation saturation of the color [0-1]
   * @param lightness  the amount of white or black applied [0-1].
   *                   Fully saturated colors have a lightness value of 1/2.
   * @param opacity    opacity (alpha) of the color, where 0.0 means fully
   *                   transparent and 1.0 fully opaque.
   * @return a Color with the provided HSLA components
   */
  public static Color hsl(double hue, double saturation, double lightness, double opacity) {
    assert hue >= 0 && hue <= 360 : "hue must be between 0.0 and 360.0";
    assert saturation >= 0 && saturation <= 1 : "saturation must be between 0.0 and 1.0";
    assert lightness >= 0 && lightness <= 1 : "lightness must be between 0.0 and 1.0";
    assert opacity >= 0 && opacity <= 1 : "opacity must be between 0.0 and 1.0";
    return new Color(hue, saturation, lightness, opacity);
  }

  /**
   * Returns a fully opaque Color with the provided hue (H), saturation (S), value (V).
   * <p>
   * https://upload.wikimedia.org/wikipedia/commons/4/4e/HSV_color_solid_cylinder.png
   *
   * @param hue        hue of the color [0-360]
   * @param saturation saturation of the color [0-1]
   * @param value      the amount of light that is applied [0-1]
   * @return a Color with the provided HSV components.
   */
  public static Color hsv(double hue, double saturation, double value) {
    return hsv(hue, saturation, value, 1);
  }

  /**
   * Returns a Color with the provided hue (H), saturation (S), value (V),
   * and a certain degree of opacity (alpha, A).
   * <p>
   * https://upload.wikimedia.org/wikipedia/commons/4/4e/HSV_color_solid_cylinder.png
   *
   * @param hue        hue of the color [0-360]
   * @param saturation saturation of the color [0-1]
   * @param value      the amount of light that is applied [0-1]
   * @param opacity    opacity (alpha) of the color, where 0.0 means fully
   *                   transparent and 1.0 fully opaque.
   * @return a Color with the provided HSVA components.
   */
  public static Color hsv(double hue, double saturation, double value, double opacity) {
    assert hue >= 0 && hue <= 360 : "hue must be between 0.0 and 360.0";
    assert saturation >= 0 && saturation <= 1 : "saturation must be between 0.0 and 1.0";
    assert value >= 0 && value <= 1 : "value must be between 0.0 and 1.0";
    assert opacity >= 0 && opacity <= 1 : "opacity must be between 0.0 and 1.0";
    return Color.fromHSVA(hue, saturation, value, opacity);
  }

}
