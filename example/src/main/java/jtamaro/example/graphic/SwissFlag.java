package jtamaro.example.graphic;

import jtamaro.graphic.Graphic;

import static jtamaro.example.Toolbelt.square;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.io.IO.show;

public final class SwissFlag {

  private SwissFlag() {
  }

  public static void main() {
    final Graphic h = rectangle(200, 60, WHITE);
    final Graphic v = rectangle(60, 200, WHITE);
    final Graphic cross = overlay(h, v);
    final Graphic background = square(320, RED);
    final Graphic flag = overlay(cross, background);
    show(flag);
  }
}
