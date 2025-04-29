package jtamaro.example.interaction;

import jtamaro.data.Function1;
import jtamaro.data.Sequence;
import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;

import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.of;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.IO.interact;

public final class Checkboxes {

  record Model(Sequence<CheckboxModel> checkboxes) {

    public Model withCheckbox(int idx, CheckboxModel checkboxModel) {
      return new Model(set(checkboxes, idx, checkboxModel));
    }
  }

  record CheckboxModel(String label, Boolean pressed) {

  }

  private static <T> T get(Sequence<T> seq, int idx) {
    return idx == 0
        ? seq.first()
        : get(seq.rest(), idx - 1);
  }

  private static <T> Sequence<T> set(Sequence<T> seq, int idx, T value) {
    return idx == 0
        ? cons(value, seq.rest())
        : cons(seq.first(), set(seq.rest(), idx - 1, value));
  }

  private static Graphic renderCheckbox(
      CheckboxModel checkboxModel,
      Function1<Function1<CheckboxModel, CheckboxModel>, Model> evolveModel
  ) {
    return new Actionable<Model>(
        overlay(
            beside(
                overlay(
                    rectangle(18, 18, checkboxModel.pressed() ? RED : WHITE),
                    rectangle(20, 20, BLACK)
                ),
                text(checkboxModel.label(), "Fira Sans", 20, BLACK)
            ),
            rectangle(200, 50, WHITE)
        )
    ).withMousePressHandler((Model m, Coordinate c, MouseButton b) ->
        evolveModel.apply(cm -> new CheckboxModel(cm.label, !cm.pressed))
    ).asGraphic();
  }

  private static Graphic renderCheckboxes(Model model) {
    return model.checkboxes().zipWithIndex().map(p -> renderCheckbox(
        p.first(),
        transform -> model.withCheckbox(
            p.second(),
            transform.apply(get(model.checkboxes, p.second())))
    )).reduce(
        emptyGraphic(),
        Graphics::beside
    );
  }

  public static void main(String[] args) {
    interact(new Model(of(
        new CheckboxModel("Hungry", true),
        new CheckboxModel("Tired", false))
    )).withRenderer(Checkboxes::renderCheckboxes).run();
  }
}
