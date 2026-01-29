package jtamaro.example.animation;

import jtamaro.data.Sequence;
import jtamaro.graphic.Color;
import jtamaro.graphic.Fonts;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.range;
import static jtamaro.example.Toolbelt.equilateralTriangle;
import static jtamaro.example.Toolbelt.get;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.hsv;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Colors.rgba;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.graphic.Points.BOTTOM_CENTER;
import static jtamaro.graphic.Points.BOTTOM_LEFT;
import static jtamaro.graphic.Points.BOTTOM_RIGHT;
import static jtamaro.graphic.Points.TOP_LEFT;
import static jtamaro.io.GraphicIO.animate;
import static jtamaro.io.GraphicIO.showFilmStrip;

public final class Fireworks {

  private static final int YELLOW_HUE = 44;

  private static final int RED_HUE = 354;

  private static final int BLUE_HUE = 199;

  private static final int GREEN_HUE = 143;

  private Fireworks() {
  }

  public static void main() {
    final Sequence<Graphic> loop = range(20).map(t -> frame(200, t / 20.0));
    showFilmStrip(loop);
    animate(loop, true, 10);
  }

  private static Graphic pytamaroLogo(double size) {
    final Color logoRed = rgb(210, 7, 29);
    final Graphic mountain = equilateralTriangle(size, logoRed);
    final Color logoBlue = rgb(0, 139, 203);
    final Graphic lake = rotate(180, equilateralTriangle(size / 2, logoBlue));
    return compose(
        pin(BOTTOM_CENTER, lake),
        pin(BOTTOM_LEFT,
            compose(
                pin(BOTTOM_RIGHT, mountain),
                pin(BOTTOM_CENTER, lake)
            )
        )
    );
  }

  private static Graphic center(double size, double time, double hue) {
    final Color color = hsv(hue, 1, 1);
    final double diameter = 1 + size / 10 * time;
    return ellipse(diameter, diameter, color);
  }

  private static Graphic streakPrecomputeColors(double angle, double hue, double time_offset, double size, double time) {
    final int BANDS = 20;
    final Sequence<Color> COLORS = range(BANDS).map(v -> hsv(hue, 1, v / (double) BANDS));
    Graphic result = emptyGraphic();
    for (int band : range(BANDS)) {
      final int c = (int) (band + 2 * BANDS - BANDS * (time + time_offset)) % BANDS;
      final Color color = get(COLORS, c);
      final double radius = 1 + size / BANDS * band;
      final Graphic sector = pin(TOP_LEFT, circularSector(radius, angle, color));
      result = compose(result, sector);
    }
    return result;
  }

  private static Graphic streak(double angle, double hue, double timeOffset, double size, double time) {
    assert time >= 0 && time <= 1;
    assert timeOffset >= 0 && timeOffset <= 1;
    final int BANDS = 20;
    Graphic result = emptyGraphic();
    for (int band : range(BANDS)) {
      final int v = (int) (band + 2 * BANDS - BANDS * (time + timeOffset)) % BANDS;
      final Color color = hsv(hue, 1, v / (double) BANDS);
      final double radius = 1 + size / BANDS * band;
      final Graphic sector = pin(BOTTOM_LEFT, circularSector(radius, angle, color));
      result = compose(result, sector);
    }
    return result;
  }

  private static Graphic streaks(int count, double angle, double hue, double timeOffset, double size, double time) {
    Graphic streaks = emptyGraphic();
    for (int s : range(count)) {
      var rotated_streak = rotate(s * 360.0 / count, streak(angle, hue, timeOffset, size, time));
      streaks = compose(streaks, rotated_streak);
    }
    return streaks;
  }

  private static Graphic fireworks(double size, double time) {
    return overlay(
        compose(
            center(size, time, YELLOW_HUE),
            compose(
                rotate(-5, streaks(12, 10, RED_HUE, 0, size, time)),
                compose(
                    rotate(-2.5 + 15, streaks(6, 5, BLUE_HUE, 1.0 / 3, size, time)),
                    rotate(-2.5 - 15, streaks(6, 5, GREEN_HUE, 2.0 / 3, size, time))
                )
            )
        ),
        rectangle(2 * size + 1, 2 * size + 1, BLACK)
    );
  }

  private static Graphic frame(double size, double time) {
    return compose(
        pin(BOTTOM_RIGHT,
            above(
                pytamaroLogo(size / 8),
                above(
                    rectangle(1, size / 40, TRANSPARENT),
                    text("Made with JTamaro", "Din Alternate", size / 20, WHITE)
                )
            )
        ),
        pin(BOTTOM_RIGHT,
            overlay(
                above(
                    text("Happy New Year!",
                        "Din Alternate",
                        size / 4,
                        rgba(255, 255, 255, 0.8)),
                    text("With Enlightening Compositions!", Fonts.SANS_SERIF, size / 8, WHITE)
                ),
                fireworks(size, time)
            )
        )
    );
  }
}
