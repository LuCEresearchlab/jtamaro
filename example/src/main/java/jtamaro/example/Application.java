package jtamaro.example;

import java.util.Map;
import jtamaro.example.animation.Clock;
import jtamaro.example.animation.CogWheel;
import jtamaro.example.animation.CountUp;
import jtamaro.example.animation.Fireworks;
import jtamaro.example.animation.NestedTriangles;
import jtamaro.example.animation.PhoneDial;
import jtamaro.example.animation.Polygons;
import jtamaro.example.animation.ThreeTwoOne;
import jtamaro.example.animation.TriangleCircles;
import jtamaro.example.color.ColorDemo;
import jtamaro.example.coordinate.CartesianDemo;
import jtamaro.example.coordinate.FunctionPlotterDemo;
import jtamaro.example.coordinate.PointSequenceDemo;
import jtamaro.example.coordinate.SimpleCartesianDemo;
import jtamaro.example.coordinate.StuffDemo;
import jtamaro.example.graphic.Alphabet;
import jtamaro.example.graphic.ChristmasTree;
import jtamaro.example.graphic.CompositionInJava;
import jtamaro.example.graphic.Daisy;
import jtamaro.example.graphic.GraphicReductions;
import jtamaro.example.graphic.PaintBrush;
import jtamaro.example.graphic.Rings;
import jtamaro.example.graphic.RotationPin;
import jtamaro.example.graphic.SwissFlag;
import jtamaro.example.graphic.TextDemo;
import jtamaro.example.graphic.TriangleDemo;
import jtamaro.example.interaction.ButtonsDemo;
import jtamaro.example.interaction.Checkboxes;
import jtamaro.example.interaction.ColorPickerDemo;
import jtamaro.example.interaction.ControllableSpinner;
import jtamaro.example.interaction.DrawingDemo;
import jtamaro.example.interaction.ElevatorDemo;
import jtamaro.example.interaction.InteractionWithBackgroundDemo;
import jtamaro.example.interaction.TextFieldDemo;
import jtamaro.example.interaction.ToDoDemo;
import jtamaro.example.music.EventDemo;
import jtamaro.example.music.MusicDemo;
import jtamaro.example.music.SwissRailwayJingle;
import jtamaro.example.music.TooSweet;
import jtamaro.example.music.TwelveBarBluesDemo;
import jtamaro.example.sequence.InsertionSort;
import jtamaro.example.sequence.QuickSort;
import jtamaro.example.sequence.Scan;

/**
 * Note: This is the main class when running with {@code gradle :example:run}. However, one can
 * e.g., run any of the demos directly from the IDE (and thanks to Gradle, the IDE should find the
 * library).
 */
public final class Application {

  private static final Map<String, Runnable> DEMO_MAP = Map.of(
      "animation", Application::animation,
      "color", Application::color,
      "coordinate", Application::coordinate,
      "graphic", Application::graphic,
      "interaction", Application::interaction,
      "music", Application::music,
      "sequence", Application::sequence
  );

  private Application() {
  }

  static void main(String[] args) {
    if (args.length < 1) {
      allDemos();
    } else if (DEMO_MAP.containsKey(args[0])) {
      DEMO_MAP.get(args[0]).run();
    } else {
      System.err.println("Unknown demo category: " + args[0]);
    }
  }

  private static void allDemos() {
    DEMO_MAP.values().forEach(Runnable::run);
  }

  private static void animation() {
    Clock.main();
    CogWheel.main();
    CountUp.main();
    Fireworks.main();
    NestedTriangles.main();
    PhoneDial.main();
    Polygons.main();
    ThreeTwoOne.main();
    TriangleCircles.main();
  }

  private static void color() {
    ColorDemo.main();
  }

  private static void coordinate() {
    CartesianDemo.main();
    FunctionPlotterDemo.main();
    PointSequenceDemo.main();
    SimpleCartesianDemo.main();
    StuffDemo.main();
  }

  private static void graphic() {
    Alphabet.main();
    ChristmasTree.main();
    CompositionInJava.main();
    Daisy.main();
    GraphicReductions.main();
    PaintBrush.main();
    Rings.main();
    RotationPin.main();
    SwissFlag.main();
    TextDemo.main();
    TriangleDemo.main();
  }

  private static void interaction() {
    ButtonsDemo.main();
    Checkboxes.main();
    ColorPickerDemo.main();
    ControllableSpinner.main();
    DrawingDemo.main();
    ElevatorDemo.main();
    InteractionWithBackgroundDemo.main();
    TextFieldDemo.main();
    ToDoDemo.main();
  }

  private static void music() {
    EventDemo.main();
    MusicDemo.main();
    SwissRailwayJingle.main();
    TooSweet.main();
    TwelveBarBluesDemo.main();
  }

  private static void sequence() {
    InsertionSort.main();
    QuickSort.main();
    Scan.main();
  }
}
