package jtamaro.example.interaction.tutorial;

import jtamaro.graphic.Graphic;

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
 * STEP 0 -- UI rendering using info from model, no interaction
 *
 * Model and UI.
 * UI performs output (renders based on model).
 * But UI does not handle input (handle events).
 */
public final class Step0 {


  public static void main() {
    interact(new Model(true, false))
      .withRenderer(Step0::ui)
      .run();
  }


  //=== Model (things that CHANGE in our app)
  record Model(boolean hungry, boolean tired) { }


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
      checkbox("Hungry", model.hungry()),
      checkbox("Tired", model.tired())
    );
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
