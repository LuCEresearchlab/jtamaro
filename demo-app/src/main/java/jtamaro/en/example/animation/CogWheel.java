package jtamaro.en.example.animation;

import jtamaro.en.Color;
import jtamaro.en.Graphic;
import jtamaro.en.Sequence;

import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.animate;
import static jtamaro.en.IO.saveAnimatedGif;
import static jtamaro.en.IO.showFilmStrip;
import static jtamaro.en.Points.BOTTOM_CENTER;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.example.Toolbelt.*;

public class CogWheel {

  private static Graphic tooth(double innerDiameter, double outerDiameter, double angle, Color color) {
    final double diameter = (innerDiameter + outerDiameter) / 2;
    final double angleRad = angle * 2 * Math.PI / 360;
    final double width = Math.sin(angleRad / 2) * diameter;
    final double height = Math.cos(angleRad / 2) * outerDiameter / 2;
    return pin(BOTTOM_CENTER, rectangle(width, height, color));
  }

  private static Graphic cogs(double innerDiameter, double outerDiameter, int toothCount, Color color) {
    final double toothAngle = 360.0 / (2 * toothCount);
    final Graphic tooth = tooth(innerDiameter, outerDiameter, toothAngle, color);
    final Sequence<Integer> angles = range(0, 360, 360 / toothCount);
    Graphic composition = emptyGraphic();
    for (final int angle : angles) {
      Graphic rotatedTooth = rotate(angle, tooth);
      composition = compose(composition, rotatedTooth);
    }
    return composition;
  }

  private static Graphic cogwheel(double innerDiameter, double outerDiameter, int toothCount, Color color) {
    return compose(
        compose(
            circle(innerDiameter, color),
            cogs(innerDiameter, outerDiameter, toothCount, color)
        ),
        circle(outerDiameter, TRANSPARENT)
    );
  }

  private static Sequence<Graphic> animation(boolean transparentBackground) {
    final int toothCount = 9;
    final double innerDiameter = 400;
    final double outerDiameter = 520;
    final Graphic wheel = cogwheel(innerDiameter, outerDiameter, toothCount, rgb(96, 96, 96));
    final Sequence<Integer> angles = range(0, 360 / toothCount, 2);
    final Graphic background = transparentBackground ? emptyGraphic() : rectangle(outerDiameter, outerDiameter, WHITE);
    return map(angle -> compose(rotate(angle, wheel), background), angles);
  }

  public static void main(String[] args) {
    Sequence<Graphic> loop = animation(true);
    Sequence<Graphic> film = cycle(loop);
    showFilmStrip(loop);
    showFilmStrip(film);
    animate(film);
    //saveAnimatedGif(animation(false), true, 25, "cogwheel.gif");
  }

}
