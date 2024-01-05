package jtamaro.en.example.animation;

import jtamaro.en.Sequence;
import jtamaro.en.Graphic;

import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.example.Toolbelt.*;


public class NestedTriangles {

  private static Graphic triangles(double angle) {
    return compose(
        ellipse(5, 5, RED),
        compose(
            isoscelesTriangle(200, angle, GREEN),
            isoscelesTriangle(250, angle, BLUE)
        )
    );
  }

  private static Sequence<Graphic> angleAnimation() {
    return map(a -> triangles(a), range(180));
  }

  public static void main(String[] args) {
    show(triangles(105));
    showFilmStrip(angleAnimation());
    animate(angleAnimation());
  }

}
