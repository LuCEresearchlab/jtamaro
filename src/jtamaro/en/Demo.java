package jtamaro.en;

import jtamaro.en.fun.Op;

public class Demo {

  public static void main(String[] args) {
    Graphic h = Op.rectangle(200, 60, Color.WHITE);
    Graphic v = Op.rectangle(60, 200, Color.WHITE);
    Graphic cross = Op.overlay(h, v);
    Graphic grund = Op.rectangle(320, 320, Color.RED);
    Graphic flag = Op.overlay(cross, grund);
    IO.show(flag);
  }
  
}
