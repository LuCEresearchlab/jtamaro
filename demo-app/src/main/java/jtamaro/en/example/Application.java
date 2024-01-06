package jtamaro.en.example;

import jtamaro.en.example.animation.*;
import jtamaro.en.example.color.*;
import jtamaro.en.example.graphic.*;
import jtamaro.en.example.interaction.*;
import jtamaro.en.example.sequence.*;


/**
 * Note: This is the main class when running with gradle :demo-app:run
 * However, one can e.g., run any of the demos directly from the IDE
 * (and thanks the gradle, the IDE should find the library).
 */
public class Application {

  public static void main(String[] args) {
    System.out.println("JTamaro Demo Application");

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
    SwissFlag.main(args);
    TextDemo.main(args);
    TriangleDemo.main(args);

    // Animations
    Clock.main(args);
    CogWheel.main(args);
    Fireworks.main(args);
    InfiniteFilm.main(args);
    NestedTriangles.main(args);
    PhoneDial.main(args);
    Polygons.main(args);
    PulsingCircle.main(args);
    ThreeTwoOne.main(args);
    TriangleCircles.main(args);

    // Interactions
    ControllableSpinner.main(args);
  }

}
