package jtamaro.interaction;

import java.awt.event.KeyEvent;
import jtamaro.data.Function2;

/**
 * Character typed on a keyboard.
 *
 * @see Interaction#withKeyTypeHandler(Function2)
 */
public record KeyboardChar(char keyChar) {

  public KeyboardChar(final KeyEvent event) {
    this(event.getKeyChar());
  }
}
