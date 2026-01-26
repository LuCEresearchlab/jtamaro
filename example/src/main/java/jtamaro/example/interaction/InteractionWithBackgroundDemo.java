package jtamaro.example.interaction;

import java.awt.event.KeyEvent;
import jtamaro.data.Pair;
import jtamaro.graphic.CartesianWorld;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.KeyboardKey;

import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.GREEN;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Points.BOTTOM_LEFT;
import static jtamaro.io.IO.interact;

public final class InteractionWithBackgroundDemo {

  private static final int WIDTH = 500;

  private static final int HEIGHT = 400;

  private static final int STEP = 10;

  private InteractionWithBackgroundDemo() {
  }

  public static void main() {
    final Graphic bg = pin(
        BOTTOM_LEFT,
        above(
            rectangle(WIDTH, HEIGHT / 2.0, BLUE),
            rectangle(WIDTH, HEIGHT / 2.0, GREEN)
        )
    );

    interact(new Pair<>(WIDTH / 2, HEIGHT / 2))
        .withBackground(bg)
        .withRenderer(p -> new CartesianWorld()
            .place(WIDTH, HEIGHT, emptyGraphic())
            .place(
                p.first(),
                p.second(),
                ellipse(STEP, STEP, RED)
            )
            .asGraphic()
        )
        .withKeyPressHandler((p, k) -> switch (k.keyCode()) {
          case KeyboardKey.LEFT, KeyEvent.VK_A -> new Pair<>(
              Math.max(0, p.first() - STEP),
              p.second()
          );
          case KeyboardKey.RIGHT, KeyEvent.VK_D -> new Pair<>(
              Math.min(WIDTH, p.first() + STEP),
              p.second()
          );
          case KeyboardKey.DOWN, KeyEvent.VK_S -> new Pair<>(
              p.first(),
              Math.max(0, p.second() - STEP)
          );
          case KeyboardKey.UP, KeyEvent.VK_W -> new Pair<>(
              p.first(),
              Math.min(HEIGHT, p.second() + STEP)
          );
          default -> p;
        })
        .run();
  }
}
