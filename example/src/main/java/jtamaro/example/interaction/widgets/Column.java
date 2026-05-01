package jtamaro.example.interaction.widgets;

import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.rectangle;

import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import jtamaro.data.Sequence;


public class Column {

  private static final int MARGIN_SIZE = 20;

  public static Graphic create(Sequence<Graphic> graphics) {
    final Graphic gap = rectangle(MARGIN_SIZE, MARGIN_SIZE, TRANSPARENT);
    return graphics.intersperse(gap).reduce(emptyGraphic(), Graphics::above);
  }

}
