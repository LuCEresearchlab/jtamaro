package jtamaro.example.animation;

import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.graphic.Fonts;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.empty;
import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.range;
import static jtamaro.data.Sequences.rangeClosed;
import static jtamaro.data.Sequences.replicate;
import static jtamaro.example.Toolbelt.circle;
import static jtamaro.example.Toolbelt.composes;
import static jtamaro.example.Toolbelt.concats;
import static jtamaro.example.Toolbelt.equilateralTriangle;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.graphic.Points.BOTTOM_CENTER;
import static jtamaro.graphic.Points.CENTER_LEFT;
import static jtamaro.graphic.Points.TOP_CENTER;
import static jtamaro.io.IO.animate;
import static jtamaro.io.IO.println;
import static jtamaro.io.IO.show;
import static jtamaro.io.IO.showFilmStrip;

public class PhoneDial {

  public static Graphic background(double diameter) {
    return overlay(
        circle(diameter * 0.5, WHITE),
        circle(diameter, BLACK)
    );
  }

  public static Graphic keyGraphic(double diameter, int number) {
    return overlay(
        text("" + number, Fonts.SANS_SERIF, (diameter / 2) * 0.3, BLACK),
        circle((diameter / 2) * 0.3, WHITE)
    );
  }

  public static Graphic shiftedKeyGraphic(double diameter, int number) {
    return pin(BOTTOM_CENTER, compose(
        pin(BOTTOM_CENTER, keyGraphic(diameter, number)),
        pin(TOP_CENTER, rectangle(0, (diameter / 2) * 0.6, TRANSPARENT))
    ));
  }

  public static Sequence<Integer> keyNumbers() {
    return rangeClosed(1, 9).concat(of(0));
  }

  public static Sequence<Pair<Integer, Double>> keyNumbersWithAngles() {
    return keyNumbers().zipWithIndex()
        .map(p -> p.withSecond(60 + p.second() * 30.0));
  }

  public static Graphic keyGraphicComposite(double diameter) {
    return composes(keyNumbersWithAngles()
        .map(e -> rotate(e.second() - 90, shiftedKeyGraphic(diameter, e.first()))));
  }

  public static Graphic indicator(double diameter) {
    return pin(CENTER_LEFT, beside(
        rectangle(diameter / 2 * 0.4, 0, BLACK),
        rotate(180 - 30, equilateralTriangle(diameter / 2 * 0.2, RED))
    ));
  }

  public static Graphic dialGraphic(double diameter, double rotation) {
    return compose(
        compose(
            rotate(-30, indicator(diameter)),
            rotate(-30 + rotation, keyGraphicComposite(diameter))
        ),
        background(diameter)
    );
  }

  public static Sequence<Graphic> dialOneDigitAnimation(double diameter, int digit) {
    final Graphic ov = text("" + digit, Fonts.SANS_SERIF, diameter / 2, BLACK);
    final int digitAngle = angleForNumber(digit);
    return range(0, -digitAngle, -5)
        .concat(replicate(-digitAngle, 20))
        .concat(range(-digitAngle, 0, 1))
        .map(angle -> overlay(ov, dialGraphic(diameter, angle)));
  }

  public static Sequence<Graphic> dialDigitsAnimation(double diameter, Sequence<Integer> digitsSequence) {
    return digitsSequence.reduce(empty(), (Integer digit, Sequence<Graphic> overall) -> concats(
        of(dialOneDigitAnimation(diameter, digit),
            replicate(dialGraphic(diameter, 0), 40),
            overall)));
  }

  public static int angleForNumber(int number) {
    return angleForNumber(number, keyNumbersWithAngles());
  }

  public static int angleForNumber(int number, Sequence<Pair<Integer, Double>> remainingPairs) {
    return remainingPairs.isEmpty()
        ? 0
        : remainingPairs.first().first() == number
            ? (int) (double) remainingPairs.first().second()
            : angleForNumber(number, remainingPairs.rest());
  }

  public static void main(String[] args) {
    println(keyNumbersWithAngles());
    println(angleForNumber(5));
    show(dialGraphic(300, 0));
    showFilmStrip(dialOneDigitAnimation(300, 5));
    animate(dialDigitsAnimation(300, of(0, 5, 8, 6, 6, 4, 0, 0, 0)), 10);
  }
}
