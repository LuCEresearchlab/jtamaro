package jtamaro.en.example.animation;

import jtamaro.en.Color;
import jtamaro.en.Graphic;
import jtamaro.en.Sequence;

import static jtamaro.en.Sequences.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;

public class Fireworks {

  private static final int YELLOW_HUE = 44;
  private static final int RED_HUE = 354;
  private static final int BLUE_HUE = 199;
  private static final int GREEN_HUE = 143;

  private static Graphic pytamaroLogo(double size) {
    var logoRed = rgb(210, 7, 29);
    var mountain = triangle(size, logoRed);
    var logoBlue = rgb(0, 139, 203);
    var lake = rotate(180, triangle(size / 2, logoBlue));
    return compose(
      pin("middle", "bottom", lake),
      pin("left", "bottom",
        compose(
          pin("right", "bottom", mountain),
          pin("middle", "bottom", lake)
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
    final Sequence<Color> COLORS = map(v -> hsv(hue, 1, v / (double)BANDS), range(BANDS));
    Graphic result = emptyGraphic();
    for (int band : range(BANDS)) {
      final int c = (int)(band + 2 * BANDS - BANDS * (time + time_offset)) % BANDS;
      final Color color = COLORS.get(c);
      final double radius = 1 + size / BANDS * band;
      final Graphic sector = pin("left", "top", circularSector(radius, angle, color));
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
      final int v = (int)(band + 2 * BANDS - BANDS * (time + timeOffset)) % BANDS;
      final Color color = hsv(hue, 1, v / (double)BANDS);
      final double radius = 1 + size / BANDS * band;
      final Graphic sector = pin("left", "top", circularSector(radius, angle, color));
      result = compose(result, sector);
    }
    return result;
  }
    
  private static Graphic streaks(int count, double angle, double hue, double timeOffset, double size, double time) {
    var streaks = emptyGraphic();
    for (var s : range(count)) {
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
          //TODO: We use opposite rotation direction from PyTamaro (fix PyTamaro?)
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
      pin("right", "bottom", 
        above(
          pytamaroLogo(size / 8),
          above(
            rectangle(1, size / 40, TRANSPARENT),
            text("Made with PyTamaro", "Din Alternate", size / 20, WHITE)
          )
        )
      ),
      pin("right", "bottom", 
        overlay(
          above(
            text("Happy New Year!", "Din Alternate", size / 4, rgb(255, 255, 255, 0.8)),
            text("With Enlightening Compositions!", "Fira Sans", size / 8, WHITE)
          ),
          fireworks(size, time)
        )
      )
    );
  }

  public static void main(String[] args) {
    animate(
      map(
        t -> frame(200, t / 20.0), 
        range(20)
      ),
      true,
      1
    );
  }

}
