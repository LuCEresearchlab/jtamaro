package jtamaro.example.animation;

import jtamaro.data.Sequence;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.range;
import static jtamaro.example.Toolbelt.composes;
import static jtamaro.example.Toolbelt.isoscelesTriangle;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.hsv;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Points.CENTER_LEFT;
import static jtamaro.io.GraphicIO.animate;
import static jtamaro.io.GraphicIO.show;
import static jtamaro.io.GraphicIO.showFilmStrip;

public final class Polygons {

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
    return composes(range(sides).map(i -> polygonFacet(radius, i, sides)));
  }

  private static Sequence<Graphic> polygonAnimation() {
    return range(3, 24).map(sides -> overlay(
        regularPolygon(250, sides),
        rectangle(500, 500, TRANSPARENT)
    ));
  }

  public static void main() {
    show(regularPolygon(200, 5));
    showFilmStrip(polygonAnimation());
    animate(polygonAnimation(), 1000);
  }

  private Polygons() {
  }
}
