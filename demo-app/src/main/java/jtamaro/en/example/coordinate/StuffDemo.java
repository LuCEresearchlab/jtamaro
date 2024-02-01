package jtamaro.en.example.coordinate;

import jtamaro.en.IO;

import static jtamaro.en.Graphics.*;

import jtamaro.en.CartesianWorld;

import static jtamaro.en.Colors.*;


public class StuffDemo {
  
  public static void main(String[] args) {
    CartesianWorld cs = new CartesianWorld()
      .withBackground(hsv(45, 0.2, 1.0))
      .withAxes(hsv(45, 0.2, 0.75))
      .withPadding(20)
      .place(50, 20, ellipse(200, 200, BLUE))
      .place(-100, 0, rectangle(20, 10, RED))
      .place(100, 100, ellipse(5, 5, RED))
      .place(50, -50, ellipse(15, 15, GREEN))
      .place(-50, -50, ellipse(15, 15, GREEN))
      ;
    IO.show(cs.asGraphic());
  }

}
