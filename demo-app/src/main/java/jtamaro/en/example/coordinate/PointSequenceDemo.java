package jtamaro.en.example.coordinate;

import static jtamaro.en.Sequences.map;
import static jtamaro.en.Sequences.range;
import static jtamaro.en.Sequences.reduce;

import jtamaro.en.CartesianWorld;
import jtamaro.en.Graphic;
import jtamaro.en.IO;
import jtamaro.en.Pair;
import jtamaro.en.Sequence;

import static jtamaro.en.Pairs.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Colors.*;


public class PointSequenceDemo {
  
  public static void main(String[] args) {
    Graphic dot = ellipse(10, 10, BLUE);
    Sequence<Pair<Double,Double>> points = map(
      i -> pair(i * 20.0, i * 10.0), 
      range(10)
    );
    CartesianWorld cs = reduce(
      new CartesianWorld(),
      (e, a) -> a.place(firstElement(e), secondElement(e), dot), 
      points
    );
    Graphic result = cs.withAxes(BLACK).withPadding(20).asGraphic();
    IO.show(result);
  }

}
