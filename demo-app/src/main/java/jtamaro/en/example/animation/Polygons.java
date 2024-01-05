package jtamaro.en.example.animation;

import jtamaro.en.Sequence;
import jtamaro.en.Graphic;

import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;
import static jtamaro.en.Points.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.example.Toolbelt.*;


public class Polygons {

  private static Graphic circleSegments(double radius, int sides) {
    return reduce(
        (Graphic a, Graphic b) -> compose(a, b),
        emptyGraphic(),
        map(
            i -> rotate(i * 360.0 / sides, circularSector(radius, 360.0 / sides, hsv(i * 360.0 / sides, 1, 1))),
            range(sides)
        )
    );
  }

  private static Graphic regularPolygonBad(double radius, int sides) {
    assert radius >= 0;
    assert sides >= 3;
    return reduce(
        (Graphic a, Graphic b) -> compose(a, b),
        emptyGraphic(),
        map(
            i -> rotate(i * 360.0 / sides, pin(BOTTOM_LEFT, isoscelesTriangle(radius, 360.0 / sides, hsv(i * 360.0 / sides, 1, 1)))),
            range(sides)
        )
    );
  }

  private static Sequence<Graphic> polygonAnimationBad() {
    return cons(
      rectangle(500, 500, TRANSPARENT), // first frame to set canvas size
      cycle(
          map(a -> regularPolygonBad(250, a), range(3, 24))
      )
    );
  }

  private static Graphic polygonFacet(double radius, int number, int sides) {
    return rotate(number * 360.0 / sides,
      pin(CENTER_LEFT, 
        rotate(-360.0 / sides / 2,
          isoscelesTriangle(radius, 360.0 / sides, hsv(number * 360.0 / sides, 1, 1))
        )
      )
    );
  }

  private static Graphic regularPolygon(double radius, int sides) {
    assert radius >= 0;
    assert sides >= 3;
    return reduce(
        (Graphic a, Graphic b) -> compose(a, b),
        emptyGraphic(),
        map(
            i -> polygonFacet(radius, i, sides),
            range(sides)
        )
    );
  }

  private static Sequence<Graphic> polygonAnimation() {
    return map(
      sides -> overlay(
        regularPolygon(250, sides),
        rectangle(500, 500, TRANSPARENT)
      ),
      range(3, 24)
    );
  }

  public static void main(String[] args) {
    show(regularPolygon(200, 5));
    showFilmStrip(polygonAnimation());
    animate(polygonAnimation(), 1000);
  }

}
