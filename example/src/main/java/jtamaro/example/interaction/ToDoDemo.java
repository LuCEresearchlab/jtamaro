package jtamaro.example.interaction;

import jtamaro.data.Function2;
import jtamaro.data.Sequence;
import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.graphic.Graphics;
import jtamaro.interaction.KeyboardChar;
import jtamaro.interaction.KeyboardKey;
import jtamaro.optics.Glasses;
import jtamaro.optics.Lens;

import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.empty;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.GREEN;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Fonts.SANS_SERIF;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.graphic.Points.CENTER_LEFT;
import static jtamaro.io.IO.interact;

public final class ToDoDemo {

  private ToDoDemo() {
  }

  public static void main() {
    interact(new Model("Hello!", empty(), ToDoDemo::addNewEntry))
        .withCanvasSize(500, 400)
        .withRenderer(ToDoDemo::render)
        .withKeyTypeHandler(ToDoDemo::onKeyType)
        .withKeyPressHandler(ToDoDemo::onKeyPress)
        .run();
  }

  private static Graphic renderInputField(Model model) {
    return compose(
        pin(CENTER_LEFT,
            text(model.currentInput, SANS_SERIF, 20, WHITE)),
        pin(CENTER_LEFT,
            rectangle(500, 50, BLACK))
    );
  }

  private static Graphic renderEntry(Lens<Model, Model, Entry, Entry> lens, Model model) {
    final Entry entry = lens.view(model);
    final Graphic textGraphic = text(entry.content, SANS_SERIF, 20, BLACK);
    final Graphic checkedGraphic = rectangle(
        20,
        20,
        entry.isChecked ? GREEN : RED
    );

    final Graphic actionableCheckbox = new Actionable<Model>(checkedGraphic)
        .withMouseReleaseHandler(($, $$) ->
            lens.then(ToDoDemo$EntryLenses.isChecked).over(b -> !b, model))
        .asGraphic();

    final Graphic entryGraphic = compose(
        pin(CENTER_LEFT,
            beside(actionableCheckbox,
                beside(rectangle(10, 0, TRANSPARENT),
                    textGraphic))),
        pin(CENTER_LEFT,
            rectangle(500, 50, WHITE))
    );

    return new Actionable<Model>(entryGraphic).withMouseReleaseHandler(
        ($, $$) -> new Model(
            entry.content,
            model.entries,
            (m, newContent) -> ToDoDemo$ModelLenses.currentInput.set(
                "",
                ToDoDemo$ModelLenses.onEnter.set(
                    ToDoDemo::addNewEntry,
                    lens.then(ToDoDemo$EntryLenses.content).set(newContent, m)
                )
            )
        )
    ).asGraphic();
  }

  private static Graphic render(Model model) {
    return ToDoDemo$ModelLenses.entriesElements.foldMap(
        renderInputField(model),
        Graphics::above,
        lens -> renderEntry(lens, model),
        model
    );
  }

  private static Model onKeyType(Model model, KeyboardChar keyboardChar) {
    final char c = keyboardChar.keyChar();
    if (Character.isLetterOrDigit(c) || Character.isSpaceChar(c)) {
      return ToDoDemo$ModelLenses.currentInput.over(
          input -> input + c,
          model
      );
    } else {
      return model;
    }
  }

  private static Model onKeyPress(Model model, KeyboardKey key) {
    return switch (key.keyCode()) {
      // Remove last char
      case KeyboardKey.BACK_SPACE -> ToDoDemo$ModelLenses.currentInput.over(
          input -> switch (input.length()) {
            case 0, 1 -> "";
            default -> input.substring(0, input.length() - 1);
          },
          model
      );
      // onEnterAction
      case KeyboardKey.ENTER -> model.onEnter.apply(model, model.currentInput);
      default -> model;
    };
  }

  private static Model addNewEntry(Model current, String content) {
    return new Model(
        "",
        cons(new Entry(current.currentInput, false), current.entries),
        ToDoDemo::addNewEntry
    );
  }

  @Glasses
  record Entry(String content, boolean isChecked) {

  }

  @Glasses
  record Model(
      String currentInput,
      Sequence<Entry> entries,
      Function2<Model, String, Model> onEnter
  ) {

  }
}
