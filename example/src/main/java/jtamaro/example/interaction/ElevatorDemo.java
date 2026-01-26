package jtamaro.example.interaction;

import jtamaro.data.Sequence;
import jtamaro.graphic.Actionable;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.KeyboardKey;
import jtamaro.io.IO;

import static jtamaro.data.Sequences.cons;
import static jtamaro.data.Sequences.empty;
import static jtamaro.data.Sequences.range;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.MAGENTA;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Fonts.MONOSPACED;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.graphic.Points.TOP_LEFT;
import static jtamaro.graphic.Points.TOP_RIGHT;

public final class ElevatorDemo {

  private ElevatorDemo() {
  }

  public static void main() {
    IO.<Elevator>interact(new RestingElevator(0, empty()))
        .withRenderer(ElevatorDemo::render)
        .withKeyReleaseHandler(ElevatorDemo::onKeypress)
        .run();
  }

  private static Graphic render(Elevator elevator) {
    final Graphic floorButtons = range(6).reduce(
        emptyGraphic(),
        (floor, g) -> above(
            above(
                new Actionable<Elevator>(overlay(
                    text(String.valueOf(floor), MONOSPACED, 12, WHITE),
                    ellipse(32, 32, elevator.currentFloor() == floor ? MAGENTA : BLUE)
                )).withMousePressHandler((c, b) -> elevator.scheduleFloor(floor))
                    .asGraphic(),
                rectangle(0, 8, TRANSPARENT)
            ),
            g
        )
    );
    return compose(
        pin(TOP_RIGHT, elevator.render()),
        pin(TOP_LEFT, floorButtons)
    );
  }

  private static Elevator onKeypress(Elevator elevator, KeyboardKey key) {
    return key.keyChar() == ' '
        ? elevator.step()
        : elevator;
  }

  interface Elevator {

    int currentFloor();

    Elevator scheduleFloor(int floor);

    Elevator step();

    Graphic render();
  }

  record MovingElevator(int currentFloor,
                        int destination,
                        Sequence<Integer> scheduled) implements Elevator {

    @Override
    public Elevator scheduleFloor(int floor) {
      final boolean alreadyInSchedule = floor == currentFloor
          || floor == destination
          || !scheduled.filter(f -> f == floor).isEmpty();
      return new MovingElevator(currentFloor,
          destination,
          alreadyInSchedule ? scheduled : cons(floor, scheduled));
    }

    @Override
    public Elevator step() {
      final int nextFloor = currentFloor < destination
          ? currentFloor + 1
          : currentFloor - 1;
      if (nextFloor == destination) {
        System.out.println("Reached floor: " + destination);
        return new RestingElevator(nextFloor, scheduled);
      } else {
        return new MovingElevator(nextFloor, destination, scheduled);
      }
    }

    @Override
    public Graphic render() {
      return above(
          text(String.format("%d --> %d (+%d more)",
                  currentFloor,
                  destination,
                  scheduled.reduce(0, (x, acc) -> acc + 1)),
              MONOSPACED,
              20,
              RED),
          overlay(
              rectangle(2, 500, WHITE),
              rectangle(200, 500, BLACK)
          )
      );
    }
  }

  record RestingElevator(int currentFloor,
                         Sequence<Integer> scheduled) implements Elevator {

    @Override
    public Elevator scheduleFloor(int floor) {
      final boolean alreadyInSchedule = floor == currentFloor
          || !scheduled.filter(f -> f == floor).isEmpty();
      return new RestingElevator(currentFloor,
          alreadyInSchedule ? scheduled : cons(floor, scheduled));
    }

    @Override
    public Elevator step() {
      if (scheduled.isEmpty()) {
        return this;
      } else {
        final int destination = scheduled.reduce(scheduled.first(), this::closestFloor);
        final Sequence<Integer> otherScheduled = scheduled.filter(f -> f != destination);
        return new MovingElevator(currentFloor, destination, otherScheduled);
      }
    }

    private int closestFloor(int a, int b) {
      return Math.abs(currentFloor - a) < Math.abs(currentFloor - b) ? a : b;
    }

    @Override
    public Graphic render() {
      return above(
          text(String.format("%d (%d scheduled)",
                  currentFloor,
                  scheduled.reduce(0, (x, acc) -> acc + 1)),
              MONOSPACED,
              20,
              RED),
          overlay(
              rectangle(190, 500, WHITE),
              rectangle(200, 500, BLACK)
          )
      );
    }
  }
}
