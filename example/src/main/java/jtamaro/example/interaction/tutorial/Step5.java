package jtamaro.example.interaction.tutorial;

import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;
import jtamaro.optics.Glasses;
import jtamaro.optics.Lens;

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
 * STEP 5 -- Using Generated Lenses
 *
 * <p>Model and UI.
 * UI performs output (renders based on model). Model can be "changed" (it's immutable, so it has to
 * be replaced) UI handles input (events), locally:
 * <ul>
 * <li>Automatic mapping of coordinates to graphics.</li>
 * <li>Lenses allow mutating appropriate part of model</li>
 * <li>Lenses are automatically generated (based on @Glasses annotations)</li>
 * </ul>
 */
public final class Step5 {


  public static void main() {
    interact(new Model(true, false)).withRenderer(Step5::ui).run();
  }


  //=== Model (things that CHANGE in our app)
  @Glasses
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
    // Note: the ModelOptics class is automatically generated, because @Glasses on Model.
    return beside(
        clickableCheckbox("Hungry", Step5$ModelOptics.hungry, model),
        clickableCheckbox("Tired", Step5$ModelOptics.tired, model)
    );
  }


  //=== UI Widget (Reusable! Can be used to update ANY Boolean of ANY Model!!!)
  private static Graphic clickableCheckbox(
      String label,
      Lens<Model, Model, Boolean, Boolean> lens,
      Model model
  ) {
    final Graphic checkboxGraphic = checkbox(label, lens.view(model));
    return new Actionable<Model>(checkboxGraphic)
        //.withMousePressHandler((Coordinate _, MouseButton _) -> lens.set(!lens.view(model), model))
        .withMousePressHandler((Coordinate _, MouseButton _) -> lens.over(
            checked -> !checked,
            model
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
