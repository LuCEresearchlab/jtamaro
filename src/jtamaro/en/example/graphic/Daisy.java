package jtamaro.en.example.graphic;

import jtamaro.en.Color;
import jtamaro.en.Graphic;
import static jtamaro.en.Sequences.range;
import static jtamaro.en.Graphics.emptyGraphic;
import static jtamaro.en.Graphics.ellipse;
import static jtamaro.en.Graphics.rotate;
import static jtamaro.en.Graphics.compose;
import static jtamaro.en.Graphics.pin;
import static jtamaro.en.Points.CENTER_LEFT;
import static jtamaro.en.Colors.WHITE;
import static jtamaro.en.Colors.hsl;
import static jtamaro.en.IO.show;


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
    return compose(ellipse(size / 4, size / 4, BUD_COLOR), flower);
  }
  
  public static void main(String[] args) {
    show(daisy(400));
  }
  
}
