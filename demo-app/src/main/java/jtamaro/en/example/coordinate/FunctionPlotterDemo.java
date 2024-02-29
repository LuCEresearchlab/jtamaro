package jtamaro.en.example.coordinate;

import jtamaro.en.CartesianWorld;
import jtamaro.en.Function1;
import jtamaro.en.Color;
import jtamaro.en.Graphic;
import jtamaro.en.Pair;
import jtamaro.en.Sequence;

import static jtamaro.en.IO.*;
import static jtamaro.en.Pairs.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Colors.*;


public class FunctionPlotterDemo {
  
  public static void main(String[] args) {
    show(
      plot(x -> Math.sin(x * x), 0, 2 * Math.PI, 1000, 200, RED)
    );
  }

  public static Graphic plot(Function1<Double,Double> f, double xMin, double xMax, int width, double yScale, Color color) {
    Graphic dot = ellipse(5, 5, color);
    Sequence<Integer> viewXs = range(width);
    Sequence<Double> xs = map(i -> i / (double)width * (xMax - xMin), viewXs);
    Sequence<Double> ys = map(x -> f.apply(x), xs);
    Sequence<Double> viewYs = map(y -> y * yScale, ys);
    Sequence<Pair<Integer,Double>> points = zip(viewXs, viewYs);
    CartesianWorld cs = reduce(
      new CartesianWorld(),
      (point, world) -> world.place(firstElement(point), secondElement(point), dot), 
      points
    );
    return cs.withAxes(BLACK).withBackground(WHITE).withPadding(20).asGraphic();
  }

}
