package jtamaro.example.graphic;

import jtamaro.graphic.Color;
import jtamaro.graphic.Colors;
import jtamaro.graphic.Graphic;

import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.triangle;
import static jtamaro.graphic.Points.BOTTOM_CENTER;
import static jtamaro.graphic.Points.BOTTOM_LEFT;
import static jtamaro.graphic.Points.BOTTOM_RIGHT;
import static jtamaro.io.GraphicIO.show;

public final class JTamaroLogo {

  // https://web.archive.org/web/20250605062810/https://www.oracle.com/a/ocom/docs/java-licensing-logo-guidelines-1908204.pdf

  private static final Color JAVA_BLUE = Colors.rgb(0x00, 0x73, 0x96);

  private static final Color JAVA_ORANGE = Colors.rgb(0xed, 0x8d, 0x00);

  private JTamaroLogo() {
  }

  public static void main() {
    show(logo(400));
  }

  private static Graphic logo(double size) {
    final Graphic lake = pin(
        BOTTOM_CENTER,
        rotate(180, triangle(size / 4, size / 4, 60, JAVA_BLUE))
    );
    final Graphic mountain = triangle(size / 2, size / 2, 60, JAVA_ORANGE);
    return compose(
        pin(BOTTOM_RIGHT,
            compose(
                lake,
                pin(BOTTOM_LEFT, mountain)
            )),
        lake
    );
  }
}
