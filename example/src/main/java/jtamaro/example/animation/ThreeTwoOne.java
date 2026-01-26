package jtamaro.example.animation;

import jtamaro.graphic.Fonts;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.of;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.YELLOW;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.IO.showFilmStrip;

public final class ThreeTwoOne {

  private static final int FONT_SIZE = 100;

  private static final int WIDTH = 600;

  private static final int HEIGHT = 400;

  private ThreeTwoOne() {
  }

  public static void main() {
    showFilmStrip(
        of("One", "Two", "Three").map(ThreeTwoOne::frame),
        WIDTH,
        HEIGHT
    );
  }

  private static Graphic frame(String text) {
    return overlay(
        text(text, Fonts.SANS_SERIF, FONT_SIZE, BLUE),
        rectangle(WIDTH, HEIGHT, YELLOW)
    );
  }
}
