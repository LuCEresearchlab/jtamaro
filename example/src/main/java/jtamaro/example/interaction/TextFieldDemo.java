package jtamaro.example.interaction;

import jtamaro.graphic.Actionable;
import jtamaro.graphic.Color;
import jtamaro.graphic.Fonts;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.KeyboardChar;
import jtamaro.interaction.KeyboardKey;
import jtamaro.interaction.MouseButton;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.IO.interact;

public class TextFieldDemo {

  // Model
  record TextField(String text, int cursor, boolean cursorVisible) {

    TextField blinkCursor() {
      return new TextField(text, cursor, !cursorVisible);
    }

    TextField cursorLeft() {
      if (cursor > 0) {
        return new TextField(text, cursor - 1, cursorVisible);
      }
      return this;
    }

    TextField cursorRight() {
      if (cursor < text.length()) {
        return new TextField(text, cursor + 1, cursorVisible);
      }
      return this;
    }

    TextField character(char c) {
      return new TextField(text.substring(0, cursor)
          + c
          + text.substring(cursor), cursor + 1, cursorVisible);
    }

    TextField backspace() {
      if (cursor > 0) {
        return new TextField(text.substring(0, cursor - 1) + text.substring(
            cursor), cursor - 1, cursorVisible);
      }
      return this;
    }

    TextField clear() {
      return new TextField("", 0, cursorVisible);
    }

    TextField random() {
      return new TextField("???", 3, cursorVisible);
    }

    TextField moveCursor(int idx) {
      return new TextField(text, idx, cursorVisible);
    }
  }


  // Event handling
  private static TextField tick(TextField textField) {
    return textField.blinkCursor();
  }

  private static TextField keyRelease(TextField textField, KeyboardKey key) {
    return switch (key.keyCode()) {
      case KeyboardKey.LEFT -> textField.cursorLeft();
      case KeyboardKey.RIGHT -> textField.cursorRight();
      case KeyboardKey.BACK_SPACE -> key.hasShiftModifier()
          ? textField.clear()
          : textField.backspace();
      default -> textField;
    };
  }

  private static TextField keyType(TextField textField, KeyboardChar key) {
    final char c = key.keyChar();
    return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == ' '
        ? textField.character(c)
        : textField;
  }

  // Rendering
  private static final int SIZE = 200;

  private static final Color CURSOR = BLUE;

  private static final Color TEXT = BLACK;

  private static final Color BACKGROUND = WHITE;

  private static Graphic renderCursorGap(int position, TextField textField) {
    Color color = position == textField.cursor && textField.cursorVisible ? CURSOR : BACKGROUND;
    return rectangle(SIZE / 10.0, SIZE, color);
  }

  private static Graphic render(TextField textField) {
    // Board area
    Graphic board = emptyGraphic();
    final int n = textField.text.length();
    int i;
    for (i = 0; i < n; i++) {
      final char c = textField.text.charAt(i);
      board = beside(board, renderCursorGap(i, textField));
      final Graphic cell = overlay(
          c != ' '
              ? text("" + c, Fonts.MONOSPACED, SIZE * 0.9, TEXT)
              : emptyGraphic(),
          rectangle(SIZE, SIZE, BACKGROUND)
      );
      final int idx = i + 1;
      final Graphic actionableCell = new Actionable<TextField>(cell)
          .withMouseReleaseHandler((t, coords, btn) ->
              t.moveCursor(switch (btn.button()) {
                case MouseButton.PRIMARY -> Math.max(0, idx - 1);
                case MouseButton.SECONDARY -> idx;
                case MouseButton.AUXILIARY -> n;
                default -> t.cursor;
              }))
          .asGraphic();
      board = beside(board, actionableCell);
    }
    board = beside(board, renderCursorGap(i, textField));

    // Action area
    final Graphic clearButton = rotate(
        45,
        overlay(
            rectangle(10, 80, RED),
            rectangle(80, 10, RED)
        )
    );
    final Graphic randomButton = text("?", Fonts.MONOSPACED, 100, WHITE);
    final boolean isEmpty = textField.text.isEmpty();
    final Graphic shownButton = new Actionable<TextField>(overlay(
        isEmpty ? randomButton : clearButton,
        rectangle(SIZE, SIZE, BLACK)))
        .withMousePressHandler((t, coords, btn) -> isEmpty ? t.random() : t.clear())
        .asGraphic();

    return beside(shownButton, board);
  }

  // Main program
  public static void main(String[] args) {
    interact(new TextField("Hi", 2, true))
        .withName("TextField")
        .withCanvasSize(SIZE * 10, SIZE)
        .withRenderer(TextFieldDemo::render)
        .withKeyReleaseHandler(TextFieldDemo::keyRelease)
        .withKeyTypeHandler(TextFieldDemo::keyType)
        .withTickHandler(TextFieldDemo::tick)
        .withMsBetweenTicks(300)
        .run();
  }
}
