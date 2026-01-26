package jtamaro.example.coordinate;

import jtamaro.data.Function1;
import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.graphic.CartesianWorld;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.range;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.io.IO.show;

public final class FunctionPlotterDemo {

  private FunctionPlotterDemo() {
  }

  public static void main() {
    show(plot(x -> Math.sin(x * x), 0, 2 * Math.PI, 1000, 200, RED));
  }

  private static Graphic plot(
      Function1<Double, Double> f,
      double xMin,
      double xMax,
      int width,
      double yScale,
      Color color
  ) {
    Graphic dot = ellipse(5, 5, color);
    Sequence<Integer> viewXs = range(width);
    Sequence<Double> xs = viewXs.map(i -> i / (double) width * (xMax - xMin));
    Sequence<Double> ys = xs.map(f);
    Sequence<Double> viewYs = ys.map(y -> y * yScale);
    Sequence<Pair<Integer, Double>> points = viewXs.zipWith(viewYs);
    CartesianWorld cs = points.reduce(
        new CartesianWorld(),
        (point, world) -> world.place(point.first(), point.second(), dot));
    return cs.withAxes(BLACK)
        .withBackground(WHITE)
        .asGraphic();
  }
}
