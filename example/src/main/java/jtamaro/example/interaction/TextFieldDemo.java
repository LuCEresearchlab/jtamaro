package jtamaro.example.interaction;

import jtamaro.graphic.Color;
import jtamaro.graphic.Fonts;
import jtamaro.graphic.Graphic;
import jtamaro.io.graphic.KeyboardChar;
import jtamaro.io.graphic.KeyboardKey;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.IO.interact;
import static jtamaro.io.IO.println;

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
  }


  // Event handling
  private static TextField tick(TextField textField) {
    println("TICK:" + textField);
    return textField.blinkCursor();
  }

  private static TextField keyRelease(TextField textField, KeyboardKey key) {
    return switch (key.keyCode()) {
      case KeyboardKey.LEFT -> textField.cursorLeft();
      case KeyboardKey.RIGHT -> textField.cursorRight();
      case KeyboardKey.BACK_SPACE -> textField.backspace();
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
    Graphic board = emptyGraphic();
    int i;
    for (i = 0; i < textField.text.length(); i++) {
      char c = textField.text.charAt(i);
      board = beside(board, renderCursorGap(i, textField));
      Graphic cell = overlay(
          c != ' ' ? text("" + c, Fonts.MONOSPACED, SIZE * 0.9, TEXT) : emptyGraphic(),
          rectangle(SIZE, SIZE, BACKGROUND)
      );
      board = beside(board, cell);
    }
    board = beside(board, renderCursorGap(i, textField));
    return board;
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
