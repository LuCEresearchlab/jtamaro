package jtamaro.example.interaction.widgets;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Fonts.SANS_SERIF;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;

import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;
import jtamaro.optics.Lens;


public class Checkbox {

  private static final int FONT_SIZE = 20;


  public static <M> Graphic create(String label, Lens<M,M,Boolean,Boolean> lens, M model) {
    final Graphic graphic = checkbox(label, lens.view(model));
    // Ensure we capture mouse events also if they happen on the background
    // (within our bounding box):
    final Graphic graphicWithBackground = overlay(
      graphic,
      rectangle(graphic.getWidth(), graphic.getHeight(), TRANSPARENT)
    );
    return new Actionable<M>(graphicWithBackground)
        .withMousePressHandler((Coordinate _, MouseButton _) -> lens.over(checked -> !checked, model))
        .asGraphic();
  }

  private static Graphic checkbox(String label, boolean checked) {
    return beside(
      beside(
        rectangle(FONT_SIZE, FONT_SIZE, checked ? RED : WHITE),
        rectangle(FONT_SIZE / 2, 0, TRANSPARENT)
      ),
      text(label, SANS_SERIF, FONT_SIZE, BLACK)
    );
  }

}
