package jtamaro.example.interaction.tutorial;

import jtamaro.data.Either;
import jtamaro.data.Option;
import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;
import jtamaro.optics.AffineTraversal;
import jtamaro.optics.Glasses;
import jtamaro.optics.Prism;
import jtamaro.optics.Setter;

import static jtamaro.data.Eithers.left;
import static jtamaro.data.Eithers.right;
import static jtamaro.data.Options.none;
import static jtamaro.data.Options.some;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Fonts.SANS_SERIF;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.GraphicIO.interact;


/**
 * STEP 9 -- Models involving Sum Types
 *
 * <p>Model and UI. UI performs output (renders based on model). Model can be "changed" (it's
 * immutable, so it has to be replaced) UI handles input (events), locally:
 * <ul>
 * <li>Automatic mapping of coordinates to graphics.</li>
 * <li>Lenses allow mutating appropriate part of model</li>
 * <li>Lenses are automatically generated (based on <code>@Glasses</code> annotations)</li>
 * <li>For hierarchical models we can compose lenses with <code>Lens.then</code></li>
 * <li>For models containing sequences of submodels, we can use <code>Traversal.foldMap</code></li>
 * <li>For models containing submodels that are sum types, we can use Prisms and AffineTraversals</li>
 * </ui>
 */
public final class Step9 {

  public static void main() {
    final Game model = new Game(
        Math.random() < 0.5
            ? new HumanPlayer(true, false)
            : new ComputerPlayer(true)
    );
    interact(model).withRenderer(Step9::ui).run();
  }

  //=== Model (things that CHANGE in our app)
  @Glasses
  record Game(Player player) {

  }

  sealed interface Player {

    public boolean happy();
  }

  @Glasses
  record HumanPlayer(boolean hungry, boolean tired) implements Player {

    public boolean happy() {
      return (!hungry) && (!tired);
    }
  }

  @Glasses
  record ComputerPlayer(boolean hasTokensLeft) implements Player {

    public boolean happy() {
      return hasTokensLeft;
    }
  }

  //=== UI (output: rendering a Graphic, input: handling mouse/key events)
  private static Graphic ui(Game model) {
    // Note: the GameOptics class is automatically generated, because @Glasses on Game.
    return above(
        above(
            label("Player Configurator"),
            playerOptions(model)
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

  private static Graphic playerOptions(Game model) {
    // Dispatch based on subtype of player:
    // - HumanPlayer --> humanPlayerOptions,
    // - ComputerPlayer --> computerPlayerOptions
    //
    // Matthias: how can we do this in general?
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
    // Joey: ideally, we'd want a way to dynamically generate such a dispatcher with the annotation
    // processor. If the type is sealed we might be able to do it (TO-DO for the future).
    // The generated code could look something like this:
    return switch (model.player) {
      case ComputerPlayer _ -> computerPlayerOptions(PRISM_PLAYER_COMPUTER, model);
      case HumanPlayer _ -> humanPlayerOptions(PRISM_PLAYER_HUMAN, model);
    };
  }

  private static Graphic humanPlayerOptions(
      AffineTraversal<Game, Game, HumanPlayer, HumanPlayer> lens,
      Game model
  ) {
    // Note: the HumanPlayerOptics class is automatically generated, because @Glasses on
    // HumanPlayer.
    return above(
        label("Human Player"),
        beside(
            // compose the lens going from the Model to the HumanPlayer with the lens going from the
            // HumanPlayer to the hungry Boolean
            optionalClickableCheckbox(
                "Hungry",
                lens.then(Step9$HumanPlayerOptics.hungry),
                model
            ),
            // compose the lens going from the Model to the HumanPlayer with the lens going from the
            // HumanPlayer to the tired Boolean
            optionalClickableCheckbox("Tired", lens.then(Step9$HumanPlayerOptics.tired), model)
        )
    );
  }

  private static Graphic computerPlayerOptions(
      AffineTraversal<Game, Game, ComputerPlayer, ComputerPlayer> lens,
      Game model
  ) {
    // Note: the ComputerPlayerOptics class is automatically generated, because @Glasses on
    // ComputerPlayer.
    return above(
        label("Computer Player"),
        // compose the lens going from the Model to the HumanPlayer with the lens going from the
        // HumanPlayer to the hungry Boolean
        optionalClickableCheckbox(
            "Has Tokens",
            lens.then(Step9$ComputerPlayerOptics.hasTokensLeft),
            model
        )
    );
  }

  //=== UI Widget (Reusable! Can be used to update ANY Boolean of ANY Model!!!)
  private static Graphic optionalClickableCheckbox(
      String label,
      AffineTraversal<Game, Game, Boolean, Boolean> trav,
      Game model
  ) {
    return trav.preview(model).fold(
        checked -> clickableCheckbox(label, checked, trav, model),
        Graphics::emptyGraphic
    );
  }

  private static Graphic clickableCheckbox(
      String label,
      boolean checked,
      Setter<Game, Game, Boolean, Boolean> setter,
      Game model
  ) {
    final Graphic checkboxGraphic = checkbox(label, checked);
    return new Actionable<Game>(checkboxGraphic)
        .withMousePressHandler((Coordinate _, MouseButton _) -> setter.set(!checked, model))
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

  // Prisms for the Game Sum type

  private static final Prism<Game, Game, HumanPlayer, HumanPlayer>
      PRISM_PLAYER_HUMAN = new Prism<>() {
    @Override
    public Either<Game, HumanPlayer> getOrModify(Game source) {
      return source.player() instanceof HumanPlayer humanPlayer
          ? right(humanPlayer)
          : left(source);
    }

    @Override
    public Option<HumanPlayer> preview(Game source) {
      return source.player instanceof HumanPlayer humanPlayer
          ? some(humanPlayer)
          : none();
    }

    @Override
    public Game review(HumanPlayer value) {
      return new Game(value);
    }
  };

  private static final Prism<Game, Game, ComputerPlayer, ComputerPlayer>
      PRISM_PLAYER_COMPUTER = new Prism<>() {
    @Override
    public Either<Game, ComputerPlayer> getOrModify(Game source) {
      return source.player() instanceof ComputerPlayer computerPlayer
          ? right(computerPlayer)
          : left(source);
    }

    @Override
    public Option<ComputerPlayer> preview(Game source) {
      return source.player() instanceof ComputerPlayer computerPlayer
          ? some(computerPlayer)
          : none();
    }

    @Override
    public Game review(ComputerPlayer value) {
      return new Game(value);
    }
  };
}
