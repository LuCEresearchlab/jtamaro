package jtamaro.example.coordinate;

import jtamaro.graphic.CartesianWorld;

import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.GREEN;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.hsv;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.io.IO.show;

public final class StuffDemo {

  private StuffDemo() {}

  public static void main() {
    CartesianWorld cs = new CartesianWorld()
        .withBackground(hsv(45, 0.2, 1.0))
        .withAxes(hsv(45, 0.2, 0.75))
        .place(50, 20, ellipse(200, 200, BLUE))
        .place(-100, 0, rectangle(20, 10, RED))
        .place(100, 100, ellipse(5, 5, RED))
        .place(50, -50, ellipse(15, 15, GREEN))
        .place(-50, -50, ellipse(15, 15, GREEN));
    show(cs.asGraphic());
  }
}
