package jtamaro.example.interaction.widgets;

import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;

import jtamaro.graphic.Graphic;


public class Margin {

  private static final int MARGIN_SIZE = 20;

  public static Graphic create(Graphic graphic) {
    final double width = graphic.getWidth() + 2 * MARGIN_SIZE;
    final double height = graphic.getHeight() + 2 * MARGIN_SIZE;
    return overlay(
      graphic,
      rectangle(width, height, TRANSPARENT)
    );
  }

}
