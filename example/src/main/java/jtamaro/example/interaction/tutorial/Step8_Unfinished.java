package jtamaro.example.interaction.tutorial;

import jtamaro.data.Option;
import jtamaro.example.Toolbelt;
import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;
import jtamaro.optics.Glasses;
import jtamaro.optics.Lens;

import static jtamaro.data.Options.some;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Fonts.SANS_SERIF;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.GraphicIO.interact;


/**
 * STEP 8 -- Models involving Options (NOT YET WORKING, TODO: generate Optics for Option<>?)
 *
 * Model and UI.
 * UI performs output (renders based on model).
 * Model can be "changed" (it's immutable, so it has to be replaced)
 * UI handles input (events), locally:
 *   Automatic mapping of coordinates to graphics.
 *   Lenses allow mutating appropriate part of model
 *   Lenses are automatically generated (based on @Glasses annotations)
 *   For hierarchical models we can compose lenses with Lens.then
 *   For models containing options of submodels, we can use???
 */
public final class Step8_Unfinished {


  public static void main() {
    final Game model = new Game(
      // almost as if we had a Sequence<Player> -- `some` instead of `of`
      some(
        new Player(true, false)
      )
    );
    interact(model).withRenderer(Step8_Unfinished::ui).run();
  }


  //=== Model (things that CHANGE in our app)
  @Glasses
  record Game(Option<Player> player) {
    // almost as if we had a Sequence<Player> -- `fold` instead of `reduce`
    public boolean everyoneHappy() { return player.fold((p) -> p.happy(), () -> true); }
  }

  @Glasses
  record Player(boolean hungry, boolean tired) {
    public boolean happy() { return (!hungry) && (!tired); }
  }


  //=== UI (output: rendering a Graphic, input: handling mouse/key events)
  private static Graphic ui(Game model) {
    // Note: the GameOptics class is automatically generated, because @Glasses on Game.
    return above(
      above(
        label("Player Configurator"),
        // almost as if we had a Sequence<Player> ???
        emptyGraphic()
        // Step8$GameOptics.playerOptionalLens.foldMap(
        //   emptyGraphic(),
        //   lens -> playerOptions("Player", lens, model),
        //   model
        // )
      ),
      label(model.everyoneHappy() ? "All players are happy" : "Someone needs care")
    );
  }

  private static Graphic label(String text) {
    return overlay(
      text(text, "Fira Sans", 24, BLACK),
      rectangle(400, 50, TRANSPARENT)
    );
  }

  private static Graphic playerOptions(String playerName, Lens<Game, Game, Player, Player> lens, Game model) {
    // Note: the PlayerOptics class is automatically generated, because @Glasses on Player.
    return above(
      label(playerName),
      beside(
        // compose the lens going from the Model to the Player with the lens going from the Player to the hungry Boolean
        clickableCheckbox("Hungry", lens.then(Step8_Unfinished$PlayerOptics.hungry), model),
        // compose the lens going from the Model to the Player with the lens going from the Player to the tired Boolean
        clickableCheckbox("Tired", lens.then(Step8_Unfinished$PlayerOptics.tired), model)
      )
    );
  }


  //=== UI Widget (Reusable! Can be used to update ANY Boolean of ANY Model!!!)
  private static Graphic clickableCheckbox(String label, Lens<Game,Game,Boolean,Boolean> lens, Game model) {
    final Graphic checkboxGraphic = checkbox(label, lens.view(model));
    return new Actionable<Game>(checkboxGraphic)
        //.withMousePressHandler((Coordinate _, MouseButton _) -> lens.set(!lens.view(model), model))
        .withMousePressHandler((Coordinate _, MouseButton _) -> lens.over(checked -> !checked, model))
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
