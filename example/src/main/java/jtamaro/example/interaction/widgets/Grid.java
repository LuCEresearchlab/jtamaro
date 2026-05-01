package jtamaro.example.interaction.widgets;

import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.data.Sequences.*;

import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import jtamaro.data.Pair;
import jtamaro.data.Sequence;


public class Grid {

  private static final int GAP_SIZE = 20;

  public static Graphic create(Sequence<Sequence<Graphic>> rows) {
    final Sequence<Double> rowHeights = rows.map(row -> row.reduce(0.0, (Graphic g, Double h) -> Math.max(h, g.getHeight())));
    final Sequence<Double> colWidths = rows.reduce(empty(), (row, acc) -> zipWithMax(acc, row.map(g -> g.getWidth())));

    final Graphic gap = rectangle(GAP_SIZE, GAP_SIZE, TRANSPARENT);

    final Sequence<Graphic> renderedRows = rows.zipWith(rowHeights).map((Pair<Sequence<Graphic>,Double> pair) -> renderRow(makeRow(pair.first(), colWidths, pair.second()), gap));
    return renderedRows.intersperse(gap).reduce(emptyGraphic(), (g, result) -> above(g, result));
  }

  public static Graphic makeCell(Graphic g, double w, double h) {
    return overlay(
      g,
      rectangle(w, h, TRANSPARENT)
    );
  }

  public static Sequence<Graphic> makeRow(Sequence<Graphic> row, Sequence<Double> colWidths, double rowHeight) {
    if (colWidths.isEmpty()) return empty();
    if (row.isEmpty()) {
      return cons(
        makeCell(emptyGraphic(), colWidths.first(), rowHeight),
        makeRow(row, colWidths.rest(), rowHeight)
      );
    }
    return cons(
      makeCell(row.first(), colWidths.first(), rowHeight),
      makeRow(row.rest(), colWidths.rest(), rowHeight)
    );
  }

  public static Graphic renderRow(Sequence<Graphic> cells, Graphic gap) {
    return cells.intersperse(gap).reduce(emptyGraphic(), (g, result) -> beside(g, result));
  }


  private static Sequence<Double> zipWithMax(Sequence<Double> a , Sequence<Double> b) {
    if (a.isEmpty()) return b;
    if (b.isEmpty()) return a;
    return cons(
      Math.max(a.first(), b.first()),
      zipWithMax(a.rest(), b.rest())
    );
  }

}
