package jtamaro.example.graphic;

import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.range;
import static jtamaro.data.Sequences.replicate;
import static jtamaro.example.Toolbelt.aboves;
import static jtamaro.example.Toolbelt.besides;
import static jtamaro.example.Toolbelt.equilateralTriangle;
import static jtamaro.example.Toolbelt.isoscelesTriangle;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.graphic.Points.BOTTOM_CENTER;
import static jtamaro.graphic.Points.BOTTOM_LEFT;
import static jtamaro.graphic.Points.BOTTOM_RIGHT;
import static jtamaro.io.GraphicIO.show;

public final class ChristmasTree {

  private static final Color LIGHT_GREEN = rgb(0, 200, 0);

  private static final Color DARK_GREEN = rgb(0, 180, 0);

  private static final Color BROWN = rgb(139, 69, 19);

  private static final Color TEXT_COLOR = rgb(255, 220, 96);

  ChristmasTree() {
  }

  public static void main() {
    show(frame(10, 100));
  }

  private static Graphic level(int upwardsTriangleCount, double triangleSide) {
    final double angle = 50;
    Graphic upwardsTriangle = rotate(-angle / 2 - 90,
        isoscelesTriangle(triangleSide, angle, LIGHT_GREEN));
    Graphic downwardsTriangle = rotate(-angle / 2 + 90,
        isoscelesTriangle(triangleSide, angle, DARK_GREEN));
    Graphic upwardsTriangles = besides(replicate(upwardsTriangle, upwardsTriangleCount));
    Graphic downwardsTriangles = besides(replicate(downwardsTriangle, upwardsTriangleCount - 1));
    return overlay(upwardsTriangles, downwardsTriangles);
  }

  private static Graphic tree(int levels, double triangleSide) {
    Graphic top = aboves(range(1, levels + 1).map(i -> level(i, triangleSide)));
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

  private static Graphic frame(int levels, double triangleSide) {
    return overlay(
        aboves(
            of(
                rectangle(0, triangleSide * 2, TRANSPARENT),
                pytamaroLogo(triangleSide),
                rectangle(1, triangleSide * 0.2, TRANSPARENT),
                text("Made with PyTamaro", "Din Alternate", triangleSide * 0.25, BLACK),
                rectangle(0, triangleSide * 2, TRANSPARENT),
                text("Merry Christmas!", "Din Alternate", triangleSide * 0.8, TEXT_COLOR),
                text("Full of Surprising Compositions",
                    "Din Alternate",
                    triangleSide * 0.45,
                    WHITE)
            )
        ),
        tree(levels, triangleSide)
    );
  }
}
