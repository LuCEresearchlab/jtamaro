package jtamaro.example.coordinate;

import jtamaro.graphic.CartesianWorld;

import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.io.IO.show;

public class SimpleCartesianDemo {

  public static void main(String[] args) {
    CartesianWorld cs = new CartesianWorld()
        .place(100, 100, ellipse(10, 10, BLUE))
        .place(50, 20, rectangle(10, 10, RED));

    show(cs.asGraphic());
  }
}
