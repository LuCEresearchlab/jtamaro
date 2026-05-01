package jtamaro.example.interaction.widgets;

import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;

import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import jtamaro.data.Sequence;


public class Row {

  private static final int MARGIN_SIZE = 20;

  public static Graphic create(Sequence<Graphic> graphics) {
    final Graphic gap = rectangle(MARGIN_SIZE, MARGIN_SIZE, TRANSPARENT);
    final Graphic raw = graphics.intersperse(gap).reduce(emptyGraphic(), Graphics::beside);
    final double width = raw.getWidth() + 2 * MARGIN_SIZE;
    final double height = raw.getHeight() + 2 * MARGIN_SIZE;
    return overlay(
      raw,
      rectangle(width, height, TRANSPARENT)
    );
  }

}
