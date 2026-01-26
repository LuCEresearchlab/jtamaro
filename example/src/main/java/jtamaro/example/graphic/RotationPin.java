package jtamaro.example.graphic;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Points.BOTTOM_CENTER;
import static jtamaro.io.IO.show;

public final class RotationPin {

  private RotationPin() {
  }

  public static void main() {
    // Test for: https://github.com/LuCEresearchlab/jtamaro/issues/1
    show(
        rotate(
            9 * 5,
            compose(
                pin(BOTTOM_CENTER, ellipse(200.0 / 3 / 2, 200.0 / 3 / 2, BLACK)),
                pin(BOTTOM_CENTER, ellipse(200.0 / 3, 200.0 / 3, WHITE)))
        )
    );
  }
}
