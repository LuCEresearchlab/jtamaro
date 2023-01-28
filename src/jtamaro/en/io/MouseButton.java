package jtamaro.en.io;

import java.awt.event.MouseEvent;


public class MouseButton {

  private final MouseEvent event;

  public MouseButton(final MouseEvent event) {
    this.event = event;
  }

  public int getButton() {
    return event.getButton();
  }

}
