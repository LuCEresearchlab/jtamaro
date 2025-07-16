package jtamaro.example.interaction;

import jtamaro.graphic.Graphic;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;
import jtamaro.optics.Lens;
import jtamaro.optics.RecordComponentLens;

import static jtamaro.example.Toolbelt.square;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.io.IO.interact;

public class ControllableSpinner {

  public record Spinner(double angle, double speed, double size) {

    public static final class Optics {

      public static final Lens<Spinner, Spinner, Double, Double> angle
          = new RecordComponentLens<>(Spinner.class, "angle");

      public static final Lens<Spinner, Spinner, Double, Double> speed
          = new RecordComponentLens<>(Spinner.class, "speed");

      public static final Lens<Spinner, Spinner, Double, Double> size
          = new RecordComponentLens<>(Spinner.class, "size");
    }
  }

  private static Graphic render(Spinner spinner) {
    return overlay(
        rotate(spinner.angle, square(spinner.size, RED)),
        rectangle(600, 400, BLUE)
    );
  }

  private static Spinner tick(Spinner spinner) {
    return Spinner.Optics.angle.over(a -> a + spinner.speed, spinner);
  }

  private static Spinner onGlobalMouseMove(Spinner spinner, Coordinate c, MouseButton btn) {
    return Spinner.Optics.speed.set(c.x() / 100.0, spinner);
  }

  public static void main(String[] args) {
    interact(new Spinner(0, 1, 100))
        .withName("Spinner")
        .withCanvasSize(600, 400)
        .withMsBetweenTicks(30)
        .withTickHandler(ControllableSpinner::tick)
        .withRenderer(ControllableSpinner::render)
        .withGlobalMouseMoveHandler(ControllableSpinner::onGlobalMouseMove)
        .run();
  }

}
