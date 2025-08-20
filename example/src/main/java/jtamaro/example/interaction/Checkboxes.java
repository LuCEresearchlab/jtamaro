package jtamaro.example.interaction;

import jtamaro.data.Sequence;
import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;
import jtamaro.optics.Glasses;
import jtamaro.optics.Lens;

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

  @Glasses
  record Model(Sequence<CheckboxModel> checkboxes) {

  }

  @Glasses
  record CheckboxModel(String label, boolean isPressed) {

  }

  public Checkboxes() {
  }

  private static Graphic renderCheckbox(CheckboxModel checkbox) {
    return overlay(
        beside(
            overlay(
                rectangle(18, 18, checkbox.isPressed ? RED : WHITE),
                rectangle(20, 20, BLACK)),
            text(checkbox.label, SANS_SERIF, 20, BLACK)
        ),
        rectangle(200, 50, WHITE)
    );
  }

  private static Graphic clickableCheckbox(
      Model model,
      Lens<Model, Model, CheckboxModel, CheckboxModel> checkboxModelLens
  ) {
    // feature 1: *find* a submodel n-layers down (and render graphics in old/plain style)
    final Graphic checkboxGraphic = renderCheckbox(checkboxModelLens.view(model));
    // feature 2: compose together a lens that can *modify* a specific submodel without manual reconstruction
    final Lens<Model, Model, Boolean, Boolean> isPressedLens = checkboxModelLens.then(
        Checkboxes$CheckboxModelLenses.isPressed);

    return new Actionable<Model>(checkboxGraphic)
        .withMousePressHandler((Coordinate c, MouseButton b) ->
            // map a function (toggle) over the boolean state of the specific checkbox
            isPressedLens.over(Checkboxes::toggle, model))
        .asGraphic();
  }

  private static boolean toggle(boolean isPressed) {
    return !isPressed;
  }

  private static Graphic renderCheckboxes(Model model) {
    // - Map from sequence of checkbox lenses to graphics
    // - Fold (compose) the graphics
    return Checkboxes$ModelLenses.checkboxesElements.foldMap(
        Graphics.emptyGraphic(),
        Graphics::beside,
        itLens -> clickableCheckbox(model, itLens),
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
