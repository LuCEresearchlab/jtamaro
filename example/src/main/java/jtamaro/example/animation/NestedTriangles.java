package jtamaro.example.animation;

import jtamaro.data.Sequence;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.range;
import static jtamaro.example.Toolbelt.isoscelesTriangle;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.GREEN;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.io.IO.animate;
import static jtamaro.io.IO.show;
import static jtamaro.io.IO.showFilmStrip;

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
    return range(180).map(NestedTriangles::triangles);
  }

  public static void main(String[] args) {
    show(triangles(105));
    showFilmStrip(angleAnimation());
    animate(angleAnimation());
  }

}
