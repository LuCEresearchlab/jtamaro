package jtamaro.example.interaction.widgets;

import jtamaro.data.Sequence;
import jtamaro.graphic.Graphic;
import jtamaro.optics.Lens;


/**
 * This is a possible GUI Widget tookit API.
 */
public class Widgets {

  public static Graphic label(String text) {
    return Label.create(text);
  }

  public static Graphic label(String text, double minWidth) {
    return Label.create(text, minWidth);
  }

  public static <M> Graphic checkbox(String label, Lens<M,M,Boolean,Boolean> lens, M model) {
    return Checkbox.create(label, lens, model);
  }

  public static <M> Graphic slider(int min, int max, Lens<M,M,Integer,Integer> lens, M model) {
    return Slider.create(min, max, lens, model);
  }

  public static Graphic margin(Graphic graphic) {
    return Margin.create(graphic);
  }

  public static Graphic row(Sequence<Graphic> graphics) {
    return Row.create(graphics);
  }

  public static Graphic column(Sequence<Graphic> graphics) {
    return Column.create(graphics);
  }

  public static Graphic grid(Sequence<Sequence<Graphic>> graphics) {
    return Grid.create(graphics);
  }

  public static Graphic modelAsText(Object model, int columns) {
    return ModelAsText.create(model, columns);
  }

}
