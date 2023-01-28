package jtamaro.en.example.animation;

import jtamaro.en.Color;
import jtamaro.en.Graphic;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;

public class Fireworks {

  private static Graphic pytamaroLogo(double size) {
    var logo_red = rgb(210, 7, 29);
    var mountain = triangle(size, logo_red);
    var logo_blue = rgb(0, 139, 203);
    var lake = rotate(180, triangle(size / 2, logo_blue));
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

  private static Graphic streak(double angle, double hue, double time_offset, double size, double time) {
    var BANDS = 20;
    //Sequence<Color> COLORS = range(BANDS).map(v -> hsv(hue, 1, v / BANDS));
    var result = emptyGraphic();
    for (var band : range(BANDS)) {
      var c = (int)(band - BANDS * (time + time_offset)) % BANDS;
      //var color = COLORS[c];
      Color color = hsv(hue, 1, c / BANDS);
      var radius = 1 + size / BANDS * band;
      var sector = pin("left", "top", circularSector(radius, angle, color));
      result = compose(result, sector);
    }
    return result;
  }
    

  private static Graphic streaks(int count, double angle, double hue, double time_offset, double size, double time) {
    var streaks = emptyGraphic();
    for (var s : range(count)) {
        var rotated_streak = rotate(s * 360 / count, streak(angle, hue, time_offset, size, time));
        streaks = compose(streaks, rotated_streak);
    }
    return streaks;
  }

  private static final int YELLOW_HUE = 44;
  private static final int RED_HUE = 354;
  private static final int BLUE_HUE = 199;
  private static final int GREEN_HUE = 143;

  private static Graphic fireworks(double size, double time) {
    return overlay(
        compose(
            center(size, time, YELLOW_HUE),
            compose(
                rotate(5, streaks(12, 10, RED_HUE, 0, size, time)),
                compose(
                    rotate(2.5 + 15, streaks(6, 5, BLUE_HUE, 1/3, size, time)),
                    rotate(2.5 - 15, streaks(6, 5, GREEN_HUE, 2/3, size, time))
                )
            )
        ),
        rectangle(size, size, BLACK)
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
                    text("Happy New Year!", "Din Alternate", 200, rgb(255, 255, 255, 0.8)),
                    text("With Enlightening Compositions!", "Fira Sans", 100, WHITE)
                ),
                fireworks(size, time)
            )
        )
    );
  }

  public static void main(String[] args) {
    show(frame(600, 0.5));
    animate(map(t -> frame(600, t / 20.0), range(20)), true, 25);
  }

}
