package jtamaro.en.io;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;


public class MouseButton {

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

}
