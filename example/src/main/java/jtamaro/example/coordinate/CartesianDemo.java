package jtamaro.example.coordinate;

import jtamaro.graphic.CartesianWorld;

import static jtamaro.data.Sequences.range;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.hsv;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.io.IO.show;

public class CartesianDemo {

  public static void main(String[] args) {
    show(range(0, 2 * Math.PI, Math.PI / 1000.0).reduce(
        new CartesianWorld()
            .withAxes(rgb(200, 200, 200))
            .withBackground(WHITE),
        (angle, cw) -> {
          final double diameter = 5 + 15 * (1 + Math.sin(6 * angle));
          /*
          System.out.printf("%.02f\t%.02f\t%.02f%n", Math.cos(angle), Math.sin(angle), diameter / 200);
          System.out.printf("%.02f%n", Math.toDegrees(angle));
           */
          return cw.place(
              100 * Math.cos(angle),
              100 * Math.sin(angle),
              ellipse(diameter, diameter, hsv(Math.toDegrees(angle), 1, 1)));
        }
    ).asGraphic());
  }
}
