package jtamaro.en.io;

import java.awt.event.KeyEvent;


public class KeyboardChar {
  
  private final KeyEvent event;

  public KeyboardChar(final KeyEvent event) {
    this.event = event;
  }

  public char getChar() {
    return event.getKeyChar();
  }

}
