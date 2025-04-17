package jtamaro.example.interaction;

import jtamaro.data.Option;
import jtamaro.graphic.Graphic;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.GREEN;
import static jtamaro.graphic.Colors.MAGENTA;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Fonts.MONOSPACED;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.graphic.Graphics.triangle;
import static jtamaro.io.IO.interact;

public class NodeContainingDemo {

  public static void main(String[] args) {
    // TODO: this is going away once the method is made package-private
    final Graphic g = above(
        beside(
            rectangle(100, 200, RED),
            ellipse(150, 150, BLUE)
        ),
        beside(
            rotate(
                180,
                triangle(100, 100, 60, GREEN)
            ),
            above(
                text("Hello", MONOSPACED, 20, BLACK),
                circularSector(200, 200, MAGENTA)
            )
        )
    );
    interact(0)
        .withRenderer(t -> g)
        .withMousePressHandler((s, coords, btn) -> {
          final Option<Graphic> selected = g.nodeContaining(
              coords.x() - g.getWidth() / 2.0,
              coords.y() - g.getHeight() / 2.0
          );
          System.out.printf("Clicked [x: %d, y: %d]%n --> %s%n",
              coords.x(), coords.y(), selected.toString());
          return s;
        })
        .run();
  }
}
