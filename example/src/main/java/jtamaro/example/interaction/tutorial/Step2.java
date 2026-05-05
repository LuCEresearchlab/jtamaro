package jtamaro.example.interaction.tutorial;

import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Fonts.SANS_SERIF;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.GraphicIO.interact;


/**
 * STEP 2 -- Using actionable to automatically map from coordinates back to graphic
 *
 * <p>Model and UI. UI performs output (renders based on model). Model can be "changed" (it's
 * immutable, so it has to be replaced) UI handles input (events), locally: Automatic mapping of
 * coordinates to graphics Problem: each model mutation needs to know how to rebuild entire model.
 */
public final class Step2 {

  public static void main() {
    interact(new Model(true, false)).withRenderer(Step2::ui).run();
  }


  //=== Model (things that CHANGE in our app)
  record Model(boolean hungry, boolean tired) {

  }


  //=== UI (output: rendering a Graphic, input: handling mouse/key events)
  private static Graphic ui(Model model) {
    return above(
        label("How do you feel?"),
        checkboxes(model)
    );
  }

  private static Graphic label(String text) {
    return overlay(
        text(text, "Fira Sans", 24, BLACK),
        rectangle(400, 50, WHITE)
    );
  }

  private static Graphic checkboxes(Model model) {
    return beside(
        hungryClickableCheckbox(model),
        tiredClickableCheckbox(model)
    );
  }

  private static Graphic hungryClickableCheckbox(Model model) {
    final Graphic checkboxGraphic = checkbox("Hungry", model.hungry());
    return new Actionable<Model>(checkboxGraphic)
        .withMousePressHandler((Coordinate _, MouseButton _) -> new Model(
            !model.hungry(),
            model.tired()
        ))
        .asGraphic();
  }

  private static Graphic tiredClickableCheckbox(Model model) {
    final Graphic checkboxGraphic = checkbox("Tired", model.tired());
    return new Actionable<Model>(checkboxGraphic)
        .withMousePressHandler((Coordinate _, MouseButton _) -> new Model(
            model.hungry(),
            !model.tired()
        ))
        .asGraphic();
  }

  private static Graphic checkbox(String label, boolean checked) {
    return overlay(
        beside(
            overlay(
                rectangle(18, 18, checked ? RED : WHITE),
                rectangle(20, 20, BLACK)
            ),
            text(label, SANS_SERIF, 20, BLACK)
        ),
        rectangle(200, 50, WHITE)
    );
  }

}
