package jtamaro.en.example.coordinate;

import jtamaro.en.IO;

import static jtamaro.en.Graphics.*;

import jtamaro.en.CartesianWorld;

import static jtamaro.en.Colors.*;


public class CartesianDemo {

  public static void main(String[] args) {
    CartesianWorld cs = new CartesianWorld()
      .place(100, 100, ellipse(10, 10, BLUE))
      .place(50, 20, rectangle(10, 10, RED))
    ;

    IO.show(cs.asGraphic());
  }

}
