package jtamaro.en.example.interaction;

import jtamaro.en.Color;
import jtamaro.en.Graphic;
import jtamaro.en.io.KeyboardKey;
import jtamaro.en.io.KeyboardChar;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.IO.*;


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
            return new TextField(text.substring(0, cursor) + c + text.substring(cursor), cursor + 1, cursorVisible);
        }
        TextField backspace() {
            if (cursor > 0) {
                return new TextField(text.substring(0, cursor - 1) + text.substring(cursor), cursor - 1, cursorVisible);
            }
            return this;
        }
    }
    
    
    // Event handling
    private static TextField tick(TextField textField) {
        System.out.println("TICK:" + textField);
        return textField.blinkCursor();
    }

    private static TextField keyRelease(TextField textField, KeyboardKey key) {
        switch (key.getCode()) {
            case KeyboardKey.LEFT: return textField.cursorLeft();
            case KeyboardKey.RIGHT: return textField.cursorRight();
            case KeyboardKey.BACK_SPACE: return textField.backspace();
        }
        return textField;
    }

    private static TextField keyType(TextField textField, KeyboardChar key) {
        final char c = key.getChar();
        if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c ==' ') {
            return textField.character(c);
        }
        return textField;
    }

    // Rendering
    private static final int SIZE = 200;
    private static final Color CURSOR = BLUE;
    private static final Color TEXT = BLACK;
    private static final Color BACKGROUND = WHITE;
    
    private static Graphic renderCursorGap(int position, TextField textField) {
        Color color = position == textField.cursor && textField.cursorVisible ? CURSOR : BACKGROUND;
        return rectangle(SIZE / 10, SIZE, color);
    }
    
    private static Graphic render(TextField textField) {
        Graphic board = emptyGraphic();
        int i;
        for (i = 0; i < textField.text.length(); i++) {
            char c = textField.text.charAt(i);
            board = beside(board, renderCursorGap(i, textField));
            Graphic cell = overlay(
                c != ' ' ? text("" + c, "Courier", SIZE * 0.9, TEXT) : emptyGraphic(),
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
