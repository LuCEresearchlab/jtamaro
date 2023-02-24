package jtamaro.en.example.animation;

import jtamaro.en.Graphic;
import static jtamaro.en.IO.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Sequences.*;


public class InfiniteFilm {
  
  private static final int FONT_SIZE = 20;
  private static final int WIDTH = 100;
  private static final int HEIGHT = 60;


  private static Graphic frame(int i) {
    return overlay(
      text(""+i, "Arial", FONT_SIZE, BLACK),
      rectangle(WIDTH, HEIGHT, YELLOW)
    );
  }

  public static void main(String[] args) {
    showFilmStrip(
      map(
        i -> frame(i),
        from(0)
      ),
      WIDTH,
      HEIGHT
    );
  }

}
