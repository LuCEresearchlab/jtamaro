package jtamaro.en.example.animation;

import jtamaro.en.Graphic;
import static jtamaro.en.IO.animate;
import static jtamaro.en.Colors.BLUE;
import static jtamaro.en.Colors.BLACK;
import static jtamaro.en.Graphics.ellipse;
import static jtamaro.en.Graphics.rectangle;
import static jtamaro.en.Graphics.overlay;
import static jtamaro.en.Sequences.cycle;
import static jtamaro.en.Sequences.range;
import static jtamaro.en.Sequences.map;
import static jtamaro.en.Sequences.concat;


public class PulsingCircle {
  
  private static final int MIN_SIZE = 20;
  private static final int MAX_SIZE = 200;
  private static final int STEP = 20;


  private static Graphic frame(int size) {
    return overlay(
      ellipse(size, size, BLUE),
      rectangle(MAX_SIZE, MAX_SIZE, BLACK)
    );
  }

  public static void main(String[] args) {
    animate(
      map(
        size -> frame(size),
        cycle(
          concat(
            range(MIN_SIZE, MAX_SIZE, STEP),
            range(MAX_SIZE, MIN_SIZE, -STEP)
          )
        )
      ),
      true,
      25
    );
  }

}
