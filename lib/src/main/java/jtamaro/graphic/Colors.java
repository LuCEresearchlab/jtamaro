package jtamaro.graphic;

import java.util.function.IntFunction;

/**
 * Procedures for working with {@link Color} objects, and well-known colors.
 *
 * @see jtamaro.graphic.Color
 */
public final class Colors {

  public static final Color BLACK = rgb(0, 0, 0);

  public static final Color BLUE = rgb(0, 0, 0xFF);

  public static final Color CYAN = rgb(0, 0xFF, 0xFF);

  public static final Color GREEN = rgb(0, 0xFF, 0);

  public static final Color MAGENTA = rgb(0xFF, 0, 0xFF);

  public static final Color RED = rgb(0xFF, 0, 0);

  public static final Color TRANSPARENT = rgba(0, 0, 0, 0);

  public static final Color WHITE = rgb(0xFF, 0xFF, 0xFF);

  public static final Color YELLOW = rgb(0xFF, 0xFF, 0);

  private Colors() {
  }

  /**
   * Returns a fully opaque Color with the given red (R), green (G), and blue (B) values.
   *
   * @param red   red component [0-255]
   * @param green green component [0-255]
   * @param blue  blue component [0-255]
   * @return a Color with the provided RGB components
   */
  public static Color rgb(int red, int green, int blue) {
    return rgba(red, green, blue, 1);
  }

  /**
   * Returns a Color with the given components for red (R), green (G), and blue (B), and a certain
   * degree of opacity (opacity, A).
   *
   * @param red     red component [0-255]
   * @param green   green component [0-255]
   * @param blue    blue component [0-255]
   * @param opacity opacity (opacity) of the color, where 0.0 means fully transparent and 1.0 fully
   *                opaque.
   * @return a Color with the provided RGBA components
   */
  public static Color rgba(int red, int green, int blue, double opacity) {
    assert red >= 0 && red <= 0xFF : "Amount of red must be between 0 and 0xFF";
    assert green >= 0 && green <= 0xFF : "Amount of green must be between 0 and 0xFF";
    assert blue >= 0 && blue <= 0xFF : "Amount of blue must be between 0 and 0xFF";
    assert opacity >= 0 && opacity <= 1 : "Opacity (opacity) must be between 0.0 and 1.0";

    return new Color(red, green, blue, opacity);
  }

  /**
   * Returns a fully opaque Color with the provided hue (H), saturation (S), lightness (L).
   *
   * <p><img src="https://upload.wikimedia.org/wikipedia/commons/3/35/HSL_color_solid_cylinder.png"
   * alt="HSL cylinder" width="250px">
   *
   * @param hue        hue of the color [0-360]
   * @param saturation saturation of the color [0-1]
   * @param lightness  the amount of white or black applied [0-1]. Fully saturated colors have a
   *                   lightness value of 1/2.
   * @return a Color with the provided HSL components
   */
  public static Color hsl(double hue, double saturation, double lightness) {
    return hsla(hue, saturation, lightness, 1.0);
  }

  /**
   * Returns a Color with the provided hue (H), saturation (S), lightness (L), and a certain degree
   * of opacity (opacity, A).
   *
   * <p><img src="https://upload.wikimedia.org/wikipedia/commons/3/35/HSL_color_solid_cylinder.png"
   * alt="HSL cylinder" width="250px">
   *
   * @param hue        hue of the color [0-360]
   * @param saturation saturation of the color [0-1]
   * @param lightness  the amount of white or black applied [0-1]. Fully saturated colors have a
   *                   lightness value of 0.5.
   * @param opacity    opacity (opacity) of the color, where 0.0 means fully transparent and 1.0
   *                   fully opaque.
   * @return a Color with the provided HSLA components
   */
  public static Color hsla(double hue, double saturation, double lightness, double opacity) {
    assert hue >= 0 && hue <= 360 : "Hue must be between 0.0 and 360.0";
    assert saturation >= 0 && saturation <= 1 : "Saturation must be between 0.0 and 1.0";
    assert lightness >= 0 && lightness <= 1 : "Lightness must be between 0.0 and 1.0";
    assert opacity >= 0 && opacity <= 1 : "Opacity must be between 0.0 and 1.0";

    // convert from RGB to HSL
    // https://en.wikipedia.org/wiki/HSL_and_HSV#HSL_to_RGB
    final double H = hue; // [0, 360]
    final double S = saturation; // [0,1]
    final double L = lightness; // [0,1]
    final IntFunction<Double> f = n -> {
      final double k = (n + H / 30) % 12;
      final double a = S * Math.min(L, 1 - L);
      return L - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
    };
    final int red = (int) Math.floor(f.apply(0) * 0xFF);
    final int green = (int) Math.floor(f.apply(8) * 0xFF);
    final int blue = (int) Math.floor(f.apply(4) * 0xFF);
    return new Color(red, green, blue, opacity);
  }

  /**
   * Returns a fully opaque Color with the provided hue (H), saturation (S), value (V).
   *
   * <p><img src="https://upload.wikimedia.org/wikipedia/commons/4/4e/HSV_color_solid_cylinder.png"
   * alt="HSV cylinder" width="250px">
   *
   * @param hue        hue of the color [0-360]
   * @param saturation saturation of the color [0-1]
   * @param value      the amount of light that is applied [0-1]
   * @return a Color with the provided HSV components.
   */
  public static Color hsv(double hue, double saturation, double value) {
    return hsva(hue, saturation, value, 1.0);
  }

  /**
   * Returns a Color with the provided hue (H), saturation (S), value (V), and a certain degree of
   * opacity (opacity, A).
   *
   * <p><img src="https://upload.wikimedia.org/wikipedia/commons/4/4e/HSV_color_solid_cylinder.png"
   * alt="HSV cylinder" width="250px">
   *
   * @param hue        hue of the color [0-360]
   * @param saturation saturation of the color [0-1]
   * @param value      the amount of light that is applied [0-1]
   * @param opacity    opacity (opacity) of the color, where 0.0 means fully transparent and 1.0
   *                   fully opaque.
   * @return a Color with the provided HSVA components.
   */
  public static Color hsva(double hue, double saturation, double value, double opacity) {
    assert hue >= 0 && hue <= 360 : "Hue must be between 0.0 and 360.0";
    assert saturation >= 0 && saturation <= 1 : "Saturation must be between 0.0 and 1.0";
    assert value >= 0 && value <= 1 : "Value must be between 0.0 and 1.0";
    assert opacity >= 0 && opacity <= 1 : "Opacity must be between 0.0 and 1.0";

    // convert from RGB to HSL
    // https://en.wikipedia.org/wiki/HSL_and_HSV#HSV_to_RGB
    final double H = hue; // [0, 360]
    final double S = saturation; // [0,1]
    final double V = value; // [0,1]
    final IntFunction<Double> f = n -> {
      final double k = (n + H / 60) % 6;
      return V - V * S * Math.max(0, Math.min(k, Math.min(4 - k, 1)));
    };
    final int red = (int) Math.floor(f.apply(5) * 0xFF);
    final int green = (int) Math.floor(f.apply(3) * 0xFF);
    final int blue = (int) Math.floor(f.apply(1) * 0xFF);
    return new Color(red, green, blue, opacity);
  }

  /**
   * Return a textual visualization for the given color following the given style.
   */
  static String format(Graphic.PropStyle style, Color color) {
    return switch (style) {
      case PLAIN -> color.toString();
      case ANSI_ESCAPE_CODES -> formatAnsiEscape(color);
      case HTML -> formatHtml(color);
    };
  }

  /**
   * Return a textual visualization for the given color with ansi escape codes that use the color
   * itself to colorize its representation.
   *
   * @hidden
   */
  public static String formatAnsiEscape(Color color) {
    return String.format(
        "Color[rgb=\033[38;2;%5$sm\033[48;2;%1$d;%2$d;%3$dm#%1$02X%2$02X%3$02X\033[0m, opacity=%4$.2f]",
        color.red(),
        color.green(),
        color.blue(),
        color.opacity(),
        isLight(color) ? "0;0;0" : "255;255;255"
    );
  }

  /**
   * Return an HTML string of a span with text color that of the given color and for content its
   * <pre>#RRGGBB</pre> representation.
   */
  private static String formatHtml(Color color) {
    return String.format(
        "<font bgcolor=\"rgba(%1$d,%2$d,%3$d,%4$.2f)\" color=\"%5$s\">&nbsp;#%1$02x%2$02x%3$02x&nbsp;</font>",
        color.red(),
        color.green(),
        color.blue(),
        color.opacity(),
        isLight(color) ? "#000000" : "#ffffff"
    );
  }

  /**
   * Convert a given jtamaro color to an equivalent awt {@link java.awt.Color}.
   */
  public static java.awt.Color toAwtColor(Color color) {
    return new java.awt.Color(color.red(), color.green(), color.blue(), color.alpha());
  }

  /**
   * Return whether the given color is light or dark.
   *
   * @implNote Formula taken from Item 3.5 of the
   * <a href="https://www.itu.int/dms_pubrec/itu-r/rec/bt/R-REC-BT.709-6-201506-I!!PDF-E.pdf">
   * Parameter values for the HDTV standards for production and international programme
   * exchange</a>
   */
  private static boolean isLight(Color color) {
    final double luminance = 0.2126 * color.red() / 255.0
        + 0.7152 * color.green() / 255.0
        + 0.0722 * color.blue() / 255.0;
    return luminance > 0.5;
  }
}
