package jtamaro.example.animation;

import jtamaro.data.Sequence;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.range;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Points.BOTTOM_CENTER;
import static jtamaro.graphic.Points.CENTER;
import static jtamaro.graphic.Points.TOP_CENTER;
import static jtamaro.io.IO.animate;
import static jtamaro.io.IO.interact;
import static jtamaro.io.IO.showFilmStrip;

public final class Clock {

  private static final Color RING_COLOR = rgb(200, 200, 200);

  private static final int HOURS_PER_CYCLE = 12;

  private static final int MINUTES_PER_CYCLE = 60;

  private static final int SECONDS_PER_CYCLE = 60;

  private Clock() {
  }

  public static void main() {
    final int diameter = 300;
    final Sequence<Graphic> animation = range(2400)
        .map(s -> clock(diameter, (s / 60) / 60, s / 60, s));
    showFilmStrip(animation);
    animate(animation, 1000);

    interact(0)
        .withCanvasSize(diameter, diameter)
        .withMsBetweenTicks(1000)
        .withTickHandler(time -> time + 1)
        .withRenderer(time -> clock(diameter, (time / 60) / 60, time / 60, time))
        .run();
  }

  private static Graphic circle(double diameter, Color color) {
    return ellipse(diameter, diameter, color);
  }

  private static int angle(int steps, int stepsPerCycle) {
    //return -360 * steps / stepsPerCycle; // PyTamaro
    return -360 * steps / stepsPerCycle;
  }

  private static Graphic background(double diameter) {
    return overlay(
        circle(diameter, WHITE),
        circle(diameter * 1.01, RING_COLOR)
    );
  }

  private static Graphic hand(double longPart, double shortPart, double width) {
    return compose(
        pin(BOTTOM_CENTER, rectangle(width, longPart, BLACK)),
        pin(TOP_CENTER, rectangle(width, shortPart, BLACK))
    );
  }

  private static Graphic minutesHand(double diameter) {
    return hand(0.46 * diameter, 0.12 * diameter, (0.052 + 0.036) / 2 * diameter);
  }

  private static Graphic hoursHand(double diameter) {
    return hand(0.32 * diameter, 0.12 * diameter, (0.064 + 0.052) / 2 * diameter);
  }

  private static Graphic secondsHand(double diameter) {
    final double width = 0.014 * diameter;
    final Graphic longPart = rectangle(width, 0.312 * diameter, RED);
    final Graphic shortPart = rectangle(width, 0.165 * diameter, RED);
    final Graphic tip = circle(0.105 * diameter, RED);
    return compose(
        pin(BOTTOM_CENTER,
            compose(
                pin(CENTER, tip),
                pin(TOP_CENTER, longPart)
            )
        ),
        pin(TOP_CENTER, shortPart)
    );
  }

  private static Graphic overlayMultiple(Sequence<Graphic> graphics) {
    Graphic result = emptyGraphic();
    for (Graphic graphic : graphics) {
      result = compose(result, graphic);
    }
    return result;
  }

  private static Graphic ticks(
      double outerRadius,
      double width,
      double length,
      int betweenAngles
  ) {
    final double innerRadius = outerRadius - length;
    final Graphic tick = rectangle(width, length, BLACK);
    final Graphic gap = rectangle(width, innerRadius, TRANSPARENT);
    final Graphic positionedTick = pin(BOTTOM_CENTER,
        compose(
            pin(BOTTOM_CENTER, tick),
            pin(TOP_CENTER, gap)
        )
    );
    return overlayMultiple(range(0, 360, betweenAngles)
        .map(w -> rotate(w, positionedTick)));
  }

  private static Graphic minutesTicks(double diameter) {
    return ticks(0.485 * diameter, 0.014 * diameter, 0.035 * diameter, 6);
  }

  private static Graphic hoursTicks(double diameter) {
    return ticks(0.485 * diameter, 0.035 * diameter, 0.12 * diameter, 30);
  }

  private static Graphic clock(double diameter, int hours, int minutes, int seconds) {
    return overlayMultiple(of(
        rotate(angle(seconds, SECONDS_PER_CYCLE), secondsHand(diameter)),
        rotate(angle(hours, HOURS_PER_CYCLE), hoursHand(diameter)),
        rotate(angle(minutes, MINUTES_PER_CYCLE), minutesHand(diameter)),
        minutesTicks(diameter),
        hoursTicks(diameter),
        background(diameter)
    ));
  }
}
