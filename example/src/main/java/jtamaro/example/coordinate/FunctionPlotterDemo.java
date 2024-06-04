package jtamaro.example.coordinate;

import jtamaro.data.Function1;
import jtamaro.data.Pair;
import jtamaro.data.Sequence;
import jtamaro.graphic.CartesianWorld;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.map;
import static jtamaro.data.Sequences.range;
import static jtamaro.data.Sequences.reduce;
import static jtamaro.data.Sequences.zip;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.io.IO.show;

public class FunctionPlotterDemo {

  public static void main(String[] args) {
    show(
        plot(x -> Math.sin(x * x), 0, 2 * Math.PI, 1000, 200, RED)
    );
  }

  public static Graphic plot(Function1<Double, Double> f, double xMin, double xMax, int width, double yScale, Color color) {
    Graphic dot = ellipse(5, 5, color);
    Sequence<Integer> viewXs = range(width);
    Sequence<Double> xs = map(i -> i / (double) width * (xMax - xMin), viewXs);
    Sequence<Double> ys = map(f, xs);
    Sequence<Double> viewYs = map(y -> y * yScale, ys);
    Sequence<Pair<Integer, Double>> points = zip(viewXs, viewYs);
    CartesianWorld cs = reduce(
        new CartesianWorld(),
        (point, world) -> world.place(point.first(), point.second(), dot),
        points
    );
    return cs.withAxes(BLACK)
        .withBackground(WHITE)
        .withPadding(20)
        .asGraphic();
  }
}
