package jtamaro.example.interaction;

import jtamaro.graphic.Graphic;

import static jtamaro.example.Toolbelt.square;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.io.IO.interact;

public class ControllableSpinner {

  record Spinner(double angle, double speed, double size) {

  }

  private static Graphic render(Spinner spinner) {
    return overlay(
        rotate(spinner.angle, square(spinner.size, RED)),
        rectangle(600, 400, BLUE)
    );
  }

  private static Spinner tick(Spinner spinner) {
    return new Spinner(spinner.angle + spinner.speed, spinner.speed, spinner.size);
  }

  public static void main(String[] args) {
    interact(new Spinner(0, 1, 100))
        .withName("Spinner")
        .withCanvasSize(600, 400)
        .withMsBetweenTicks(30)
        .withTickHandler(ControllableSpinner::tick)
        .withRenderer(ControllableSpinner::render)
        .withGlobalMouseMoveHandler((wheel, c, btn) -> new Spinner(wheel.angle, c.x() / 100.0, c.y()))
        .run();
  }

}
