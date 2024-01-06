package jtamaro.en.example.animation;

import static jtamaro.en.Pairs.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;
import static jtamaro.en.Points.*;

import jtamaro.en.Pair;
import jtamaro.en.Sequence;
import jtamaro.en.Graphic;
import static jtamaro.en.example.Toolbelt.*;


public class PhoneDial {

  public static Graphic background(double diameter) {
        return overlay(
            circle(diameter * 0.5, WHITE),
            circle(diameter, BLACK)
        );
    }
    
    public static Graphic keyGraphic(double diameter, int number) {
        return overlay(
            text(""+number, "Helvetica", (diameter / 2) * 0.3, BLACK),
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
      return concat(rangeClosed(1, 9), of(0));
    }

    public static Sequence<Pair<Integer,Double>> keyNumbersWithAngles() {
      return map(
        (Pair<Integer,Integer> p) -> pair(firstElement(p),
        60 + secondElement(p) * 30.0), zipWithIndex(keyNumbers())
      );
    }

    public static Graphic keyGraphicComposite(double diameter) {
      return reduce(
        (a, e) -> compose(
          rotate(secondElement(e) - 90, shiftedKeyGraphic(diameter, firstElement(e))), 
          a
        ), 
        emptyGraphic(), 
        keyNumbersWithAngles()
      );
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
      final Graphic ov = text(""+digit, "Helvetica", diameter / 2, BLACK);
      final int digitAngle = angleForNumber(digit);
      return map(
        angle -> overlay(
          ov,
          dialGraphic(diameter, angle)
        ),
        concat(
          range(0, -digitAngle, -5),
          concat(
            replicate(-digitAngle, 20),
            range(-digitAngle, 0, 1)
          )
        )
      );
    }

    public static Sequence<Graphic> dialDigitsAnimation(double diameter, Sequence<Integer> digitsSequence) {
      return reduce(
        (Sequence<Graphic> overall, Integer digit) -> concat(
          concat(
            overall,
            replicate(dialGraphic(diameter, 0), 40)
          ),
          dialOneDigitAnimation(diameter, digit)
        ),
        empty(),
        digitsSequence
      );
    }

    public static int angleForNumber(int number) {
      return angleForNumber(number, keyNumbersWithAngles());
    }

    public static int angleForNumber(int number, Sequence<Pair<Integer,Double>> remainingPairs) {
      return isEmpty(remainingPairs)
        ? 0
        : firstElement(first(remainingPairs)) == number
          ? (int)(double)secondElement(first(remainingPairs))
          : angleForNumber(number, rest(remainingPairs));
    }

    public static void main(String[] args) {
      println(keyNumbersWithAngles());
      System.out.println(angleForNumber(5));
      show(dialGraphic(300, 0));
      showFilmStrip(dialOneDigitAnimation(300, 5));
      animate(dialDigitsAnimation(300, of(0, 5, 8, 6, 6, 4, 0, 0, 0)), 10);
    }
    
}