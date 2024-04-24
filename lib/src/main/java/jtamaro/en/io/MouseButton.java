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

  private final int button;

  public MouseButton(final MouseEvent event) {
    this(event.getButton());
  }

  public MouseButton(final int button) {
    this.button = button;
  }

  public int getButton() {
    return button;
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
