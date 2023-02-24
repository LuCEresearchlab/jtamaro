package jtamaro.en.example.animation;

import jtamaro.en.Graphic;
import static jtamaro.en.IO.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Sequences.*;


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
        text -> frame(text),
        of("One", "Two", "Three")
      ),
      WIDTH,
      HEIGHT
    );
  }

}
