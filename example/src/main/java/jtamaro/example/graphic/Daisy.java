package jtamaro.example.graphic;

import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.range;
import static jtamaro.example.Toolbelt.circle;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.hsl;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Points.CENTER_LEFT;
import static jtamaro.io.IO.show;

public class Daisy {

  private static final Color BUD_COLOR = hsl(48, 0.98, 0.47);

  private static Graphic petal(double size) {
    return pin(CENTER_LEFT, ellipse(size, size / 4, WHITE));
  }

  private static Graphic daisy(double size) {
    Graphic flower = emptyGraphic();
    for (int angle : range(0, 360, 30)) {
      flower = compose(flower, rotate(angle, petal(size / 2)));

    }
    return compose(circle(size / 4, BUD_COLOR), flower);
  }

  public static void main(String[] args) {
    show(daisy(400));
  }

}
