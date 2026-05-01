package jtamaro.example.interaction.tutorial;

import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;
import jtamaro.optics.Glasses;

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
 * STEP 4 -- Lenses combining getters & setters
 *
 * Model and UI.
 * UI performs output (renders based on model).
 * Model can be "changed" (it's immutable, so it has to be replaced)
 * UI handles input (events), locally:
 *   Automatic mapping of coordinates to graphics.
 *   Lenses allow mutating appropriate part of model
 */
public final class Step4 {


  public static void main() {
    interact(new Model(true, false)).withRenderer(Step4::ui).run();
  }


  //=== Model (things that CHANGE in our app)
  @Glasses
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
      clickableCheckbox("Hungry", new HungryLens(), model),
      clickableCheckbox("Tired", new TiredLens(), model)
    );
  }

  // A Lens combines a getter and a setter
  public static interface Lens<M,E> {
    public E view(M m);
    public M set(E e, M m);
  }
  public static class HungryLens implements Lens<Model, Boolean> {
    public Boolean view(Model m) { return m.hungry(); }
    public Model set(Boolean h, Model m) { return new Model(h, m.tired()); }
  }
  public static class TiredLens implements Lens<Model, Boolean> {
    public Boolean view(Model m) { return m.tired(); }
    public Model set(Boolean t, Model m) { return new Model(m.hungry(), t); }
  }

  //=== UI Widget (Reusable! Can be used to update ANY Boolean of ANY Model!!!)
  private static Graphic clickableCheckbox(String label, Lens<Model,Boolean> lens, Model model) {
    final Graphic checkboxGraphic = checkbox(label, lens.view(model));
    return new Actionable<Model>(checkboxGraphic)
        .withMousePressHandler((Coordinate _, MouseButton _) -> lens.set(!lens.view(model), model))
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
