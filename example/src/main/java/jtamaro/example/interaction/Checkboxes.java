package jtamaro.example.interaction;

import jtamaro.data.Sequence;
import jtamaro.data.Sequences;
import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;
import jtamaro.optics.Getter;
import jtamaro.optics.Lens;
import jtamaro.optics.RecordComponentLens;

import static jtamaro.data.Sequences.of;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Fonts.SANS_SERIF;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.IO.interact;

public final class Checkboxes {

  record Model(Sequence<CheckboxModel> checkboxes) {

    static final class Optics {

      public static final Lens<Model, Model, Sequence<CheckboxModel>, Sequence<CheckboxModel>>
          checkboxes = new RecordComponentLens<>(Model.class, "checkboxes");
    }
  }

  record CheckboxModel(String label, boolean pressed) {

    static final class Optics {

      public static final Lens<CheckboxModel, CheckboxModel, String, String>
          label = new RecordComponentLens<>(CheckboxModel.class, "label");

      public static final Lens<CheckboxModel, CheckboxModel, Boolean, Boolean>
          pressed = new RecordComponentLens<>(CheckboxModel.class, "pressed");
    }
  }

  public Checkboxes() {
  }

  private static Graphic renderCheckbox(
      Model model,
      Getter<Model, String> label,
      Lens<Model, Model, Boolean, Boolean> pressed
  ) {
    final String labelTxt = label.view(model);
    final boolean isPressed = pressed.view(model);
    return new Actionable<Model>(
        overlay(
            beside(
                overlay(
                    rectangle(18, 18, isPressed ? RED : WHITE),
                    rectangle(20, 20, BLACK)),
                text(labelTxt, SANS_SERIF, 20, BLACK)
            ),
            rectangle(200, 50, WHITE)
        )
    ).withMousePressHandler((Model m, Coordinate c, MouseButton b) ->
        pressed.set(!isPressed, model)
    ).asGraphic();
  }

  private static Graphic renderCheckboxes(Model model) {
    return Sequences.combineTraversal(Model.Optics.checkboxes).foldMap(
        Graphics.emptyGraphic(),
        Graphics::beside,
        itLens -> renderCheckbox(
            model,
            itLens.then(CheckboxModel.Optics.label),
            itLens.then(CheckboxModel.Optics.pressed)
        ),
        model
    );
  }

  public static void main(String[] args) {
    interact(new Model(of(
        new CheckboxModel("Hungry", true),
        new CheckboxModel("Tired", false))
    )).withRenderer(Checkboxes::renderCheckboxes).run();
  }
}
