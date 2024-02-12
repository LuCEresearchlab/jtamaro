package jtamaro.en.io;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;


public class KeyboardKey {

  public static final int LEFT = KeyEvent.VK_LEFT;
  public static final int RIGHT = KeyEvent.VK_RIGHT;
  public static final int UP = KeyEvent.VK_UP;
  public static final int DOWN = KeyEvent.VK_DOWN;
  public static final int DELETE = KeyEvent.VK_DELETE;
  public static final int BACK_SPACE = KeyEvent.VK_BACK_SPACE;
  public static final int SHIFT = KeyEvent.VK_SHIFT;
  public static final int CONTROL = KeyEvent.VK_CONTROL;
  public static final int ALT = KeyEvent.VK_ALT;
  public static final int META = KeyEvent.VK_META; // "command" on Mac
  public static final int ENTER = KeyEvent.VK_ENTER; // "return" on Mac
  public static final int ESCAPE = KeyEvent.VK_ESCAPE;
  public static final int TAB = KeyEvent.VK_TAB;

  private final KeyEvent event;

  public KeyboardKey(final KeyEvent event) {
    this.event = event;
  }

  public KeyboardKey(final int keyCode) {
    this.event = new KeyEvent(new JComponent() {}, 0, 0, 0, keyCode, KeyEvent.CHAR_UNDEFINED);
  }

  public int getCode() {
    return event.getKeyCode();
  }

}
