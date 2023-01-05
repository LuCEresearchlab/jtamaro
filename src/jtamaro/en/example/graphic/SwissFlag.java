package jtamaro.en.example.graphic;

import jtamaro.en.Color;
import jtamaro.en.Graphic;
import jtamaro.en.IO;
import jtamaro.en.fun.Op;


public class SwissFlag {

  public static void main(String[] args) {
    Graphic h = Op.rectangle(200, 60, Color.WHITE);
    Graphic v = Op.rectangle(60, 200, Color.WHITE);
    Graphic cross = Op.overlay(h, v);
    Graphic square = Op.rectangle(320, 320, Color.RED);
    Graphic flag = Op.overlay(cross, square);
    IO.show(flag);
  }
  
}
