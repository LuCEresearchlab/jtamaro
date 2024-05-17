package jtamaro.en.io;

import java.awt.event.KeyEvent;
import java.util.Objects;


public final class KeyboardChar {

  private final char keyChar;

  public KeyboardChar(final KeyEvent event) {
    this.keyChar = event.getKeyChar();
  }

  public KeyboardChar(final char keyChar) {
    this.keyChar = keyChar;
  }

  public char getChar() {
    return keyChar;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o == null || getClass() != o.getClass()) {
      return false;
    } else {
      final KeyboardChar that = (KeyboardChar) o;
      return keyChar == that.keyChar;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(keyChar);
  }

  @Override
  public String toString() {
    return "KeyboardChar[char=" + keyChar + "]";
  }
}
