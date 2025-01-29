package jtamaro.example;

import jtamaro.example.animation.Clock;
import jtamaro.example.animation.CogWheel;
import jtamaro.example.animation.Fireworks;
import jtamaro.example.animation.NestedTriangles;
import jtamaro.example.animation.PhoneDial;
import jtamaro.example.animation.Polygons;
import jtamaro.example.animation.ThreeTwoOne;
import jtamaro.example.animation.TriangleCircles;
import jtamaro.example.color.ColorDemo;
import jtamaro.example.graphic.Alphabet;
import jtamaro.example.graphic.ChristmasTree;
import jtamaro.example.graphic.Daisy;
import jtamaro.example.graphic.GraphicReductions;
import jtamaro.example.graphic.PaintBrush;
import jtamaro.example.graphic.Rings;
import jtamaro.example.graphic.RotationPin;
import jtamaro.example.graphic.SwissFlag;
import jtamaro.example.graphic.TextDemo;
import jtamaro.example.graphic.TriangleDemo;
import jtamaro.example.interaction.ControllableSpinner;
import jtamaro.example.sequence.ChessPositions;
import jtamaro.example.sequence.Demo;
import jtamaro.example.sequence.Digits;
import jtamaro.example.sequence.ForLoops;
import jtamaro.example.sequence.InsertionSort;
import jtamaro.example.sequence.QuickSort;
import jtamaro.example.sequence.Scan;

import static jtamaro.io.IO.println;


/**
 * Note: This is the main class when running with gradle :demo-app:run However, one can e.g., run
 * any of the demos directly from the IDE (and thanks the gradle, the IDE should find the library).
 */
public class Application {

  public static void main(String[] args) {
    println("JTamaro Demo Application");

    // Sequence
    ChessPositions.main(args);
    Demo.main(args);
    Digits.main(args);
    ForLoops.main(args);
    InsertionSort.main(args);
    QuickSort.main(args);
    GraphicReductions.main(args);
    Scan.main(args);

    // Colors
    ColorDemo.main(args);

    // Graphic
    Alphabet.main(args);
    ChristmasTree.main(args);
    Daisy.main(args);
    GraphicReductions.main(args);
    PaintBrush.main(args);
    Rings.main(args);
    SwissFlag.main(args);
    TextDemo.main(args);
    TriangleDemo.main(args);
    RotationPin.main(args);

    // Animations
    Clock.main(args);
    CogWheel.main(args);
    Fireworks.main(args);
    NestedTriangles.main(args);
    PhoneDial.main(args);
    Polygons.main(args);
    ThreeTwoOne.main(args);
    TriangleCircles.main(args);

    // Interactions
    ControllableSpinner.main(args);
  }

}
