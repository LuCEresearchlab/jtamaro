package jtamaro.en.example.animation;

import jtamaro.en.Graphic;

import static jtamaro.en.Colors.BLACK;
import static jtamaro.en.Colors.BLUE;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.animate;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.example.Toolbelt.*;


public class PulsingCircle {

  private static final int MIN_SIZE = 20;
  private static final int MAX_SIZE = 200;
  private static final int STEP = 20;


  private static Graphic frame(int size) {
    return overlay(
        circle(size, BLUE),
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
        )
    );
  }

}
