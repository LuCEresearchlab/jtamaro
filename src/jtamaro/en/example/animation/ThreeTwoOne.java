package jtamaro.en.example.animation;

import jtamaro.en.Graphic;
import static jtamaro.en.IO.showFilmStrip;
import static jtamaro.en.Colors.BLUE;
import static jtamaro.en.Colors.YELLOW;
import static jtamaro.en.Graphics.text;
import static jtamaro.en.Graphics.rectangle;
import static jtamaro.en.Graphics.overlay;
import static jtamaro.en.Sequences.of;
import static jtamaro.en.Sequences.map;


public class ThreeTwoOne {
  
  private static final int FONT_SIZE = 100;
  private static final int WIDTH = 600;
  private static final int HEIGHT = 400;


  private static Graphic frame(String text) {
    return overlay(
      text(text, "Arial", FONT_SIZE, BLUE),
      rectangle(WIDTH, HEIGHT, YELLOW)
    );
  }

  public static void main(String[] args) {
    showFilmStrip(
      map(
        size -> frame(size),
        of("One", "Two", "Three")
      ),
      WIDTH,
      HEIGHT
    );
  }

}
