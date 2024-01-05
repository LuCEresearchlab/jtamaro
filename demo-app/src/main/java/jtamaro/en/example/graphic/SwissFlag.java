package jtamaro.en.example.graphic;

import jtamaro.en.Graphic;

import static jtamaro.en.Colors.RED;
import static jtamaro.en.Colors.WHITE;
import static jtamaro.en.Graphics.overlay;
import static jtamaro.en.Graphics.rectangle;
import static jtamaro.en.IO.show;
import static jtamaro.en.example.Toolbelt.*;

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
