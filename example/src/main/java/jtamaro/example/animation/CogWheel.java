package jtamaro.example.animation;

import jtamaro.data.Sequence;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.range;
import static jtamaro.example.Toolbelt.circle;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Points.BOTTOM_CENTER;
import static jtamaro.io.IO.animate;
import static jtamaro.io.IO.showFilmStrip;

public final class CogWheel {

  private CogWheel() {
  }

  public static void main() {
    final Sequence<Graphic> loop = animation(true);
    showFilmStrip(loop);
    animate(loop);
  }

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
    final Graphic background = transparentBackground
        ? emptyGraphic()
        : rectangle(outerDiameter, outerDiameter, WHITE);
    return angles.map(angle -> compose(rotate(angle, wheel), background));
  }
}
