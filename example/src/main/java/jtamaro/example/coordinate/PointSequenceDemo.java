package jtamaro.example.coordinate;

import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.graphic.CartesianWorld;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.range;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.io.GraphicIO.show;

public final class PointSequenceDemo {

  private PointSequenceDemo() {
  }

  public static void main() {
    final Graphic dot = ellipse(10, 10, BLUE);
    final Sequence<Pair<Double, Double>> points = range(10)
        .map(i -> new Pair<>(i * 20.0, i * 10.0));
    final CartesianWorld cs = points.reduce(
        new CartesianWorld(),
        (e, a) -> a.place(e.first(), e.second(), dot)
    );
    final Graphic result = cs.withAxes(BLACK)
        .asGraphic();
    show(result);
  }
}
