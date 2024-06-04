package jtamaro.example.graphic;

import jtamaro.graphic.Graphic;

import static jtamaro.example.Toolbelt.square;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.io.IO.show;

public class SwissFlag {

  public static void main(String[] args) {
    Graphic h = rectangle(200, 60, WHITE);
    Graphic v = rectangle(60, 200, WHITE);
    Graphic cross = overlay(h, v);
    Graphic background = square(320, RED);
    Graphic flag = overlay(cross, background);
    show(flag);
  }
}
