package jtamaro.en.bigbang;

import java.awt.event.KeyEvent;


public class KeyboardKey {

  private final KeyEvent event;

  public KeyboardKey(final KeyEvent event) {
    this.event = event;
  }

  public int getCode() {
    return event.getKeyCode();
  }

}
