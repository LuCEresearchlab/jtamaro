package jtamaro.en.example.interaction;

import jtamaro.en.Graphic;

import static jtamaro.en.Colors.BLUE;
import static jtamaro.en.Colors.RED;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.interact;
import static jtamaro.en.example.Toolbelt.*;

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
        .withMouseMoveHandler((wheel, c) -> new Spinner(wheel.angle, c.x() / 100.0, c.y()))
        .run();
  }

}
