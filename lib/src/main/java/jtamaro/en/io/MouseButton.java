package jtamaro.en.io;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;


public final class MouseButton {
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

  private final MouseEvent event;

  public MouseButton(final MouseEvent event) {
    this.event = event;
  }

  public MouseButton(final int button) {
    this.event = new MouseEvent(new JComponent() {}, 0, 0, 0, 0, 0, 0, false, button);
  }

  public int getButton() {
    return event.getButton();
  }

  @Override
  public String toString() {
    final int button = event.getButton();
    return switch (button) {
      case PRIMARY -> "MouseButton.PRIMARY";
      case AUXILIARY -> "MouseButton.AUXILIARY";
      case SECONDARY -> "MouseButton.SECONDARY";
      default -> "MouseButton[button=" + button + "]";
    };
  }
}
