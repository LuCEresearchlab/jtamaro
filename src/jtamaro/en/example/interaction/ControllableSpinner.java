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
    //TODO turn into fluent API -- that way BigBang can be immutable!
      BigBang<Spinner> bang = new BigBang<>();
      bang.setName("Spinner");
      bang.setCanvasSize(600, 400);
      bang.setMsBetweenTicks(30);
      bang.setInitialModel(new Spinner(0, 1, 100));
      bang.setTickHandler(ControllableSpinner::tick);
      bang.setRenderer(ControllableSpinner::render);
      bang.setMouseMoveHandler((wheel,c) -> new Spinner(wheel.angle, c.x() / 100.0, c.y()));
      bang.run();
  }
  
}
