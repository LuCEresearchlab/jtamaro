package jtamaro.example.coordinate;

import jtamaro.graphic.CartesianWorld;

import static jtamaro.data.Sequences.range;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.hsv;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.io.IO.show;

public final class CartesianDemo {

  private CartesianDemo() {
  }

  public static void main() {
    show(range(0, 2 * Math.PI, Math.PI / 1000.0).reduce(
        new CartesianWorld()
            .withAxes(rgb(200, 200, 200))
            .withBackground(WHITE),
        (angle, cw) -> {
          final double diameter = 5 + 15 * (1 + Math.sin(6 * angle));
          return cw.place(
              100 * Math.cos(angle),
              100 * Math.sin(angle),
              ellipse(diameter, diameter, hsv(Math.toDegrees(angle), 1, 1)));
        }
    ).asGraphic());
  }
}
