package jtamaro.en;

import jtamaro.en.bigbang.BigBang;
import jtamaro.en.fun.Op;

public class DemoBigBang {

  /*
  class Ufo {
      private int height;
      public Ufo(int height) {
          this.height = height;
      }
      public int height() {
          return height;
      }
  }
  */

  record Wheel(double angle, double speed, double diameter) {}

  private static Graphic render(Wheel wheel) {
      return Op.overlay(
        Op.rotate(wheel.angle, Op.rectangle(wheel.diameter, wheel.diameter, Color.RED)),
        Op.rectangle(600, 400, Color.BLUE)
      );
  }
  
  private static Wheel tick(Wheel wheel) {
      return new Wheel(wheel.angle + wheel.speed, wheel.speed, wheel.diameter);
  }
  
  public static void main(String[] args) {
      BigBang<Wheel> bang = new BigBang<>();
      bang.setName("Wheel");
      bang.setRenderingSize(600, 400);
      bang.setMsBetweenTicks(30);
      bang.setInitialModel(new Wheel(0, 1, 100));
      bang.setTickHandler(DemoBigBang::tick);
      bang.setRenderer(DemoBigBang::render);
      bang.setMouseMoveHandler((wheel,c) -> new Wheel(wheel.angle, c.x() / 100.0, c.y()));
      bang.run();
  }
  
}
