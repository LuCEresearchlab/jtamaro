package jtamaro.en.example.graphic;

import jtamaro.en.Graphic;
import jtamaro.en.Color;

import static jtamaro.en.Sequences.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Points.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;


public class ChristmasTree {

  private static Color LIGHT_GREEN = rgb(0, 200, 0);
  private static Color DARK_GREEN = rgb(0, 180, 0);
  private static Color BROWN = rgb(139, 69, 19);
  private static Color TEXT_COLOR = rgb(255, 220, 96);


  private static Graphic isoscelesTriangle(double side, double angle, Color color) {
    return triangle(side, side, angle, color);
  }

  private static Graphic level(int upwardsTriangleCount, double triangleSide) {
    final double angle = 50;
    Graphic upwardsTriangle = rotate(-angle/2-90, isoscelesTriangle(triangleSide, angle, LIGHT_GREEN));
    Graphic downwardsTriangle = rotate(-angle/2+90, isoscelesTriangle(triangleSide, angle, DARK_GREEN));
    Graphic upwardsTriangles = reduce((a, e) -> beside(a, e), emptyGraphic(), replicate(upwardsTriangle, upwardsTriangleCount));
    Graphic downwardsTriangles = reduce((a, e) -> beside(a, e), emptyGraphic(), replicate(downwardsTriangle, upwardsTriangleCount - 1));
    return overlay(upwardsTriangles, downwardsTriangles);
  }

  private static Graphic tree(int levels, double triangleSide) {
    Graphic top = reduce((a, e) -> above(a, e), emptyGraphic(), map(i -> level(i, triangleSide), range(1, levels + 1)));
    Graphic stem = rectangle(triangleSide, triangleSide, BROWN);
    return above(top, stem);
  }

  private static Graphic pytamaroLogo(double size) {
    var logoRed = rgb(210, 7, 29);
    var mountain = equilateralTriangle(size, logoRed);
    var logoBlue = rgb(0, 139, 203);
    var lake = rotate(180, equilateralTriangle(size / 2, logoBlue));
    return compose(
        pin(BOTTOM_CENTER, lake),
        pin(BOTTOM_LEFT,
            compose(
                pin(BOTTOM_RIGHT, mountain),
                pin(BOTTOM_CENTER, lake)
            )
        )
    );
  }

  private static Graphic frame(int levels, double triangleSide, double time) {
    return overlay(
      above(
        rectangle(0, triangleSide * 2, TRANSPARENT),
        above(
          above(
            above(
              pytamaroLogo(triangleSide),
              above(
                rectangle(1, triangleSide * 0.2, TRANSPARENT),
                text("Made with PyTamaro", "Din Alternate", triangleSide * 0.25, BLACK)
              )
            ),
            rectangle(0, triangleSide * 2, TRANSPARENT)
          ),
          above(
              text("Merry Christmas!", "Din Alternate", triangleSide * 0.8, TEXT_COLOR),
              text("Full of Surprising Compositions", "Din Alternate", triangleSide * 0.45, WHITE)
          )
        )
      ),
      tree(levels, triangleSide)
    );
  }

  public static void main(String[] args) {
    show(frame(10, 100, 0));
  }

}
