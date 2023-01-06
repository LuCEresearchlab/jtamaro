package jtamaro.en.example.interaction;

import jtamaro.en.Color;
import jtamaro.en.Graphic;
import jtamaro.en.bigbang.BigBang;
import jtamaro.en.fun.Op;


public class ControllableSpinner {

  record Spinner(double angle, double speed, double size) {}

  private static Graphic render(Spinner spinner) {
      return Op.overlay(
        Op.rotate(spinner.angle, Op.rectangle(spinner.size, spinner.size, Color.RED)),
        Op.rectangle(600, 400, Color.BLUE)
      );
  }
  
  private static Spinner tick(Spinner spinner) {
      return new Spinner(spinner.angle + spinner.speed, spinner.speed, spinner.size);
  }
  
  public static void main(String[] args) {
      new BigBang<>(new Spinner(0, 1, 100))
        .withName("Spinner")
        .withCanvasSize(600, 400)
        .withMsBetweenTicks(30)
        .withTickHandler(ControllableSpinner::tick)
        .withRenderer(ControllableSpinner::render)
        .withMouseMoveHandler((wheel,c) -> new Spinner(wheel.angle, c.x() / 100.0, c.y()))
        .run();
  }
  
}
