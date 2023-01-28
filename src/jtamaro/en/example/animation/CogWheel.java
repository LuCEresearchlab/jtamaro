package jtamaro.en.example.animation;

import jtamaro.en.Color;
import jtamaro.en.Graphic;
import jtamaro.en.Sequence;

import static jtamaro.en.IO.animate;
import static jtamaro.en.Colors.TRANSPARENT;
import static jtamaro.en.Colors.rgb;
import static jtamaro.en.Graphics.ellipse;
import static jtamaro.en.Graphics.rectangle;
import static jtamaro.en.Graphics.pin;
import static jtamaro.en.Graphics.compose;
import static jtamaro.en.Graphics.emptyGraphic;
import static jtamaro.en.Graphics.rotate;
import static jtamaro.en.Sequences.range;
import static jtamaro.en.Sequences.map;
import static jtamaro.en.Sequences.cycle;


public class CogWheel {
  
  private static Graphic circle(double diameter, Color color) {
    return ellipse(diameter, diameter, color);
  }

  private static Graphic tooth(double innerDiameter, double outerDiameter, double angle, Color color) {
    final double diameter = (innerDiameter + outerDiameter) / 2;
    final double angle_rad = angle * 2 * Math.PI / 360;
    final double width = Math.sin(angle_rad / 2) * diameter;
    final double height = Math.cos(angle_rad / 2) * outerDiameter / 2;
    return pin("middle", "bottom", rectangle(width, height, color));
  }

  private static Graphic cogs(double innerDiameter, double outerDiameter, int toothCount, Color color) {
    final double tooth_angle = 360 / (2 * toothCount);
    final Graphic tooth = tooth(innerDiameter, outerDiameter, tooth_angle, color);
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

  private static Sequence<Graphic> animation() {
    final int toothCount = 9;
    final double innerDiameter = 400;
    final double outerDiameter = 520;
    final Graphic wheel = cogwheel(innerDiameter, outerDiameter, toothCount, rgb(96, 96, 96));
    final Sequence<Integer> angles = range(0, 360 / toothCount, 2);
    return map(angle -> rotate(angle, wheel), angles);
  }

  public static void main(String[] args) {
    animate(
      cycle(animation())
    );
  }

}
