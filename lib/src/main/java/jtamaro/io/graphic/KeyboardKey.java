package jtamaro.io.graphic;

import java.awt.event.KeyEvent;
import jtamaro.data.Function2;

/**
 * Keyboard key that was either pressed or released.
 *
 * @see Interaction#withKeyPressHandler(Function2)
 * @see Interaction#withKeyReleaseHandler(Function2)
 */
public record KeyboardKey(int keyCode, char keyChar) {

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


  public KeyboardKey(KeyEvent event) {
    this(event.getKeyCode(), event.getKeyChar());
  }

  public KeyboardKey(int keyCode) {
    this(keyCode, KeyEvent.CHAR_UNDEFINED);
  }

  @Override
  public String toString() {
    return switch (keyCode) {
      case LEFT -> "KeyboardKey.LEFT";
      case RIGHT -> "KeyboardKey.RIGHT";
      case UP -> "KeyboardKey.UP";
      case DOWN -> "KeyboardKey.DOWN";
      case DELETE -> "KeyboardKey.DELETE";
      case BACK_SPACE -> "KeyboardKey.BACK_SPACE";
      case SHIFT -> "KeyboardKey.SHIFT";
      case CONTROL -> "KeyboardKey.CONTROL";
      case ALT -> "KeyboardKey.ALT";
      case META -> "KeyboardKey.META";
      case ENTER -> "KeyboardKey.ENTER";
      case ESCAPE -> "KeyboardKey.ESCAPE";
      case TAB -> "KeyboardKey.TAB";
      default -> "KeyboardKey[char=" + keyChar + ", code=" + keyCode + "]";
    };
  }
}
