package jtamaro.example.interaction.widgets;

import static jtamaro.graphic.Points.BOTTOM_LEFT;
import static jtamaro.graphic.Points.TOP_LEFT;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Fonts.MONOSPACED;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.graphic.Graphics.pin;

import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;


public class ModelAsText {

  private static final int FONT_SIZE = 15;
  private static final int GAP_HEIGHT = 5;
  private static final Color COLOR = rgb(100, 100, 100);

  public static Graphic create(Object model, int columns) {
    final String string = model.toString();
    final String emptyLine = "_".repeat(columns);
    final double width = text(emptyLine, MONOSPACED, FONT_SIZE, COLOR).getWidth();
    final Graphic gap = rectangle(width, GAP_HEIGHT, TRANSPARENT);
    Graphic result = emptyGraphic();
    for (int i = 0; i < string.length(); i += columns) {
      final String chunk = string.substring(i, Math.min(i + columns, string.length()));
      final Graphic line = text(chunk, MONOSPACED, FONT_SIZE, COLOR);
      if (i != 0) {
        result = compose(
          pin(BOTTOM_LEFT, result),
          pin(TOP_LEFT, gap)
        );
      }
      result = compose(
        pin(BOTTOM_LEFT, result),
        pin(TOP_LEFT, line)
      );
    }
    return result;
  }

}
