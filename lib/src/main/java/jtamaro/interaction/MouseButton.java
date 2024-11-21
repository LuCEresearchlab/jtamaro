package jtamaro.interaction;

import java.awt.event.MouseEvent;
import jtamaro.data.Function3;

/**
 * Mouse (or trackpad) button that was either pressed or released.
 *
 * @param button One of {@link MouseButton#PRIMARY}, {@link MouseButton#AUXILIARY} or
 *               {@link MouseButton#SECONDARY}
 * @see Interaction#withMousePressHandler(Function3)
 * @see Interaction#withMouseReleaseHandler(Function3)
 */
public record MouseButton(int button) {

  /**
   * Primary mouse button (usually the left button).
   */
  public static final int PRIMARY = MouseEvent.BUTTON1;

  /**
   * Auxiliary mouse button (usually the wheel button or the middle button).
   */
  public static final int AUXILIARY = MouseEvent.BUTTON2;

  /**
   * Secondary mouse button (usually the right button).
   */
  public static final int SECONDARY = MouseEvent.BUTTON3;

  public MouseButton(final MouseEvent event) {
    this(event.getButton());
  }

  @Override
  public String toString() {
    return switch (button) {
      case PRIMARY -> "MouseButton.PRIMARY";
      case AUXILIARY -> "MouseButton.AUXILIARY";
      case SECONDARY -> "MouseButton.SECONDARY";
      default -> "MouseButton[button=" + button + "]";
    };
  }
}
