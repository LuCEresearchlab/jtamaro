package jtamaro.en.io;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;


public final class KeyboardChar {

  private final KeyEvent event;

  public KeyboardChar(final KeyEvent event) {
    this.event = event;
  }

  public KeyboardChar(final char theChar) {
    this.event = new KeyEvent(new JComponent() {}, 0, 0, 0, KeyEvent.VK_UNDEFINED, theChar);
  }

  public char getChar() {
    return event.getKeyChar();
  }

}
