package jtamaro.interaction;

import java.awt.event.MouseEvent;

/**
 * Mouse (or trackpad) button that was either pressed or released.
 *
 * @param button One of {@link MouseButton#PRIMARY}, {@link MouseButton#AUXILIARY} or
 *               {@link MouseButton#SECONDARY} or {@link MouseButton#NO_BUTTON} if no button is
 *               involved in the action.
 * @see MouseAction
 */
public record MouseButton(int button) {

  /**
   * No button.
   */
  public static final int NO_BUTTON = MouseEvent.NOBUTTON;

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
      case NO_BUTTON -> "MouseButton.NO_BUTTON";
      case PRIMARY -> "MouseButton.PRIMARY";
      case AUXILIARY -> "MouseButton.AUXILIARY";
      case SECONDARY -> "MouseButton.SECONDARY";
      default -> "MouseButton[button=" + button + "]";
    };
  }
}
