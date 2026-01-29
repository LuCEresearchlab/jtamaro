package jtamaro.example.interaction;

import jtamaro.data.Sequence;
import jtamaro.graphic.Actionable;
import jtamaro.graphic.CartesianWorld;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.MouseButton;

import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.of;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.io.GraphicIO.interact;

public final class DrawingDemo {

  private DrawingDemo() {
  }

  public static void main() {
    interact(new Drawing(of()))
        .withRenderer(DrawingDemo::renderDrawing)
        .withKeyPressHandler((d, k) -> new Drawing(of()))
        .run();
  }

  private static Graphic renderDrawing(Drawing drawing) {
    final CartesianWorld world = drawing.points().reduce(
        new CartesianWorld(),
        (point, w) -> w.place(point.x(), point.y(), ellipse(10, 10, BLUE))
    ).place(0, 0, rectangle(400, 200, WHITE));

    return new Actionable<Drawing>(world.asGraphic())
        .withMouseDragHandler((Coordinate c, MouseButton b) ->
            drawing.addPoint(new Point(c.x(), c.y())))
        .asGraphic();
  }

  private record Point(double x, double y) {

  }

  private record Drawing(Sequence<Point> points) {

    public Drawing addPoint(Point p) {
      return new Drawing(cons(p, points));
    }
  }
}
