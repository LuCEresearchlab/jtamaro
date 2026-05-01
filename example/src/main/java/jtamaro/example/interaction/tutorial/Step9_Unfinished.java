package jtamaro.example.interaction.tutorial;

import jtamaro.data.Function1;
import jtamaro.data.Function2;
import jtamaro.data.Option;
import jtamaro.example.Toolbelt;
import jtamaro.example.interaction.tutorial.Step9_Unfinished.ComputerPlayer;
import jtamaro.example.interaction.tutorial.Step9_Unfinished.HumanPlayer;
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

import java.util.Map;


/**
 * STEP 9 -- Models involving Sum Types
 *
 * Model and UI.
 * UI performs output (renders based on model).
 * Model can be "changed" (it's immutable, so it has to be replaced)
 * UI handles input (events), locally:
 *   Automatic mapping of coordinates to graphics.
 *   Lenses allow mutating appropriate part of model
 *   Lenses are automatically generated (based on @Glasses annotations)
 *   For hierarchical models we can compose lenses with Lens.then
 *   For models containing submodels that are sum types, we can use???
 */
public final class Step9_Unfinished {


  public static void main() {
    final Game model = new Game(
      Math.random() < 0.5
        ? new HumanPlayer(true, false)
        : new ComputerPlayer(true)
    );
    interact(model).withRenderer(Step9_Unfinished::ui).run();
  }


  //=== Model (things that CHANGE in our app)
  @Glasses
  record Game(Player player) {
  }

  interface Player {
    public boolean happy();
  }

  @Glasses
  record HumanPlayer(boolean hungry, boolean tired) implements Player {
    public boolean happy() { return (!hungry) && (!tired); }
  }

  @Glasses
  record ComputerPlayer(boolean hasTokensLeft) implements Player {
    public boolean happy() { return hasTokensLeft; }
  }


  //=== UI (output: rendering a Graphic, input: handling mouse/key events)
  private static Graphic ui(Game model) {
    // Note: the GameOptics class is automatically generated, because @Glasses on Game.
    return above(
      above(
        label("Player Configurator"),
        playerOptions(Step9_Unfinished$GameOptics.player, model)
      ),
      label(model.player().happy() ? "Players is happy" : "Player needs care")
    );
  }

  private static Graphic label(String text) {
    return overlay(
      text(text, "Fira Sans", 24, BLACK),
      rectangle(400, 50, TRANSPARENT)
    );
  }

  private static Graphic playerOptions(Lens<Game, Game, Player, Player> lens, Game model) {
    // Dispatch based on subtype of player: HumanPlayer --> humanPlayerOptions, ComputerPlayer --> computerPlayerOptions
    //
    // TODO: how can we do this in general?
    //
    // At least by providing some kind of "lens downcaster" that could be used like this?
    //
    // final Player player = lens.view(model);
    // if (player instanceof HumanPlayer) {
    //   return humanPlayerOptions(lens.downcast(HumanPlayer.class), model);
    // } else if (player instanceof ComputerPlayer) {
    //   return computerPlayerOptions(lens.downcast(ComputerPlayer.class), model);
    // } else {
    //   return emptyGraphic();
    // }
    //
    // But better as a kind of map like this?
    //
    // return DISPATCH(Map.of(
    //   HumanPlayer.class, Step9::humanPlayerOptions,
    //   ComputerPlayer.class, Step9::computerPlayerOptions
    // ));

    final Player player = lens.view(model);
    if (player instanceof HumanPlayer) {
      // hLens downcasts from Player to HumanPlayer where needed
      final Lens<Game, Game, HumanPlayer, HumanPlayer> hLens = new Lens<>() {
        @Override
        public HumanPlayer view(Game source) {
          return (HumanPlayer)lens.view(source);
        }
        @Override
        public Game over(Function1<HumanPlayer, HumanPlayer> lift, Game source) {
          return lens.over((Player p) -> lift.apply((HumanPlayer)p), source);
        }
      };
      return humanPlayerOptions(hLens, model);
    } else if (player instanceof ComputerPlayer) {
      // cLens downcasts from Player to ComputerPlayer where needed
      final Lens<Game, Game, ComputerPlayer, ComputerPlayer> cLens = new Lens<>() {
        @Override
        public ComputerPlayer view(Game source) {
          return (ComputerPlayer)lens.view(source);
        }
        @Override
        public Game over(Function1<ComputerPlayer, ComputerPlayer> lift, Game source) {
          return lens.over((Player p) -> lift.apply((ComputerPlayer)p), source);
        }
      };
      return computerPlayerOptions(cLens, model);
    } else {
      // should never happen
      return emptyGraphic();
    }
  }

  private static Graphic humanPlayerOptions(Lens<Game, Game, HumanPlayer, HumanPlayer> lens, Game model) {
    // Note: the HumanPlayerOptics class is automatically generated, because @Glasses on HumanPlayer.
    return above(
      label("Human Player"),
      beside(
        // compose the lens going from the Model to the HumanPlayer with the lens going from the HumanPlayer to the hungry Boolean
        clickableCheckbox("Hungry", lens.then(Step9_Unfinished$HumanPlayerOptics.hungry), model),
        // compose the lens going from the Model to the HumanPlayer with the lens going from the HumanPlayer to the tired Boolean
        clickableCheckbox("Tired", lens.then(Step9_Unfinished$HumanPlayerOptics.tired), model)
      )
    );
  }

  private static Graphic computerPlayerOptions(Lens<Game, Game, ComputerPlayer, ComputerPlayer> lens, Game model) {
    // Note: the ComputerPlayerOptics class is automatically generated, because @Glasses on ComputerPlayer.
    return above(
      label("Computer Player"),
      // compose the lens going from the Model to the HumanPlayer with the lens going from the HumanPlayer to the hungry Boolean
      clickableCheckbox("Has Tokens", lens.then(Step9_Unfinished$ComputerPlayerOptics.hasTokensLeft), model)
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
