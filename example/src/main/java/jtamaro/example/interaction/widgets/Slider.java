package jtamaro.example.interaction.widgets;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Points.CENTER_LEFT;
import static jtamaro.graphic.Points.CENTER_RIGHT;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;

import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;
import jtamaro.optics.Lens;


public class Slider {

  private static final int WIDTH = 256;
  private static final int HEIGHT = 20;
  private static final int RAIL_HEIGHT = 2;
  private static final int KNOB_WIDTH = 10;

  public static <M> Graphic create(int min, int max, Lens<M,M,Integer,Integer> lens, M model) {
    final Graphic graphic = slider(min, max, lens.view(model));
    return new Actionable<M>(graphic)
        .withMouseDragHandler((Coordinate c, MouseButton _) -> update(min, max, c, lens, model))
        .asGraphic();
  }

  private static <M> M update(int min, int max, Coordinate c, Lens<M,M,Integer,Integer> lens, M model) {
    final int viewX = c.x() - KNOB_WIDTH / 2;
    final int range = max - min;
    final int modelX = min + (int)Math.round(viewX * range / (double)WIDTH);
    final int boundedModelX = Math.min(max, Math.max(min, modelX));
    return lens.set(boundedModelX, model);
  }

  private static Graphic slider(int min, int max, int current) {
    assert current >= min;
    assert current <= max;
    final int range = max - min;
    final Graphic knob = rectangle(KNOB_WIDTH, HEIGHT, RED);
    final Graphic leftGap = rectangle((current - min) * WIDTH / range, 0, TRANSPARENT);
    return compose(
        pin(CENTER_LEFT, compose(
          pin(CENTER_RIGHT, leftGap),
          pin(CENTER_LEFT, knob)
        )),
        pin(CENTER_LEFT, overlay(
          rectangle(WIDTH, RAIL_HEIGHT, BLACK),
          rectangle(WIDTH + KNOB_WIDTH, HEIGHT, TRANSPARENT)
        ))
    );
  }

}
