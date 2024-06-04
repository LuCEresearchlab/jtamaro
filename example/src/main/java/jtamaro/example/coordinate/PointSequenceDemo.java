package jtamaro.example.coordinate;

import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.graphic.CartesianWorld;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.map;
import static jtamaro.data.Sequences.range;
import static jtamaro.data.Sequences.reduce;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.io.IO.show;

public class PointSequenceDemo {

  public static void main(String[] args) {
    Graphic dot = ellipse(10, 10, BLUE);
    Sequence<Pair<Double, Double>> points = map(
        i -> new Pair<>(i * 20.0, i * 10.0),
        range(10)
    );
    CartesianWorld cs = reduce(
        new CartesianWorld(),
        (e, a) -> a.place(e.first(), e.second(), dot),
        points
    );
    Graphic result = cs.withAxes(BLACK)
        .withPadding(20)
        .asGraphic();
    show(result);
  }
}
