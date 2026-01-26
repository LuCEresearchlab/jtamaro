package jtamaro.example.interaction;

import jtamaro.data.Sequence;
import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.KeyboardKey;

import static jtamaro.data.Sequences.of;
import static jtamaro.example.Toolbelt.aboves;
import static jtamaro.example.Toolbelt.besides;
import static jtamaro.example.Toolbelt.hgap;
import static jtamaro.example.Toolbelt.vgap;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Fonts.SANS_SERIF;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.IO.interact;

public final class ButtonsDemo {

  private ButtonsDemo() {
  }

  public static void main() {
    interact(Model.initialModel())
        .withKeyPressHandler(ButtonsDemo::handleKey)
        .withRenderer(Model::render)
        .run();
  }

  private static Model handleKey(Model model, KeyboardKey key) {
    return key.keyChar() == ' '
        ? Model.initialModel()
        : model;
  }

  public record Button(String label) {

    public Graphic render(Model model) {
      final Graphic graphic = overlay(
          text(label, SANS_SERIF, 10, WHITE),
          rectangle(200, 50, BLACK)
      );

      return new Actionable<Model>(graphic)
          .withMousePressHandler(((_, _) -> model.setChoice(label)))
          .asGraphic();
    }
  }

  public record Model(
      String choice,
      String previousChoice,
      Sequence<Button> buttons
  ) {

    public static Model initialModel() {
      return new Model(
          "???",
          "???",
          of(
              new Button("DOG"),
              new Button("CAT")
          )
      );
    }

    public Model setChoice(String newChoice) {
      return new Model(newChoice, this.choice, this.buttons);
    }

    public Graphic render() {
      final Graphic header = aboves(of(
          text("Current choice: " + this.choice, SANS_SERIF, 30, BLACK),
          vgap(10),
          text("Previous choice: " + this.previousChoice, SANS_SERIF, 20, BLACK),
          vgap(20)
      ));
      final Graphic buttons = besides(
          this.buttons
              .map(b -> b.render(this))
              .intersperse(hgap(20))
      );
      final Graphic foreground = above(header, buttons);
      final Graphic background = rectangle(500, 400, WHITE);
      return overlay(
          foreground,
          background
      );
    }
  }
}
