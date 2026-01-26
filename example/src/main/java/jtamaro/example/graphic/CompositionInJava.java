package jtamaro.example.graphic;

import jtamaro.data.Sequence;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.range;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.graphic.Points.CENTER_LEFT;
import static jtamaro.io.IO.show;

public final class CompositionInJava {

  private CompositionInJava() {
  }

  public static void main() {
    final Sequence<Color> artsyRainbow = of(
        rgb(128, 24, 30),
        rgb(183, 43, 31),
        rgb(194, 61, 72),
        rgb(215, 24, 83),
        rgb(237, 66, 98),
        rgb(234, 96, 40),
        rgb(250, 153, 22),
        rgb(254, 175, 22),
        rgb(255, 200, 34),
        rgb(252, 210, 3),
        rgb(253, 237, 45),
        rgb(141, 206, 175),
        rgb(66, 180, 137),
        rgb(5, 124, 130),
        rgb(17, 96, 79),
        rgb(3, 80, 68),
        rgb(35, 53, 133),
        rgb(38, 87, 161),
        rgb(67, 121, 191),
        rgb(101, 132, 200),
        rgb(124, 108, 178),
        rgb(100, 77, 154),
        rgb(58, 36, 104),
        rgb(36, 31, 59)
    );

    final double radius = 800;
    final double fontSize = radius * 0.3;
    final double lineWidth = 2;
    final int rings = 25;
    final Graphic logo = overlay(
        above(
            renderText("Composition", "Fira Sans", fontSize, lineWidth),
            renderText("in Java", "Fira Sans", fontSize, lineWidth)
        ),
        colorRings(rings, lineWidth, radius, artsyRainbow)
    );
    final double logoWidth = logo.getWidth();
    show(overlay(logo, rectangle(logoWidth / 0.75, logoWidth, TRANSPARENT)));
    show(colorRings(rings, lineWidth, radius, artsyRainbow));
  }

  private static Graphic renderText(String text, String font, double size, double lineWidth) {
    Graphic offset = rectangle(lineWidth * 2, lineWidth * 2, TRANSPARENT);
    Graphic bgText = text(text, font, size, BLACK);
    Graphic tr = beside(offset, above(bgText, offset));
    Graphic tl = beside(above(bgText, offset), offset);
    Graphic br = beside(offset, above(offset, bgText));
    Graphic bl = beside(above(offset, bgText), offset);
    return overlay(
        text(text, font, size, WHITE),
        overlay(overlay(tr, tl), overlay(br, bl))
    );
  }

  private static <T> int length(Sequence<T> sequence) {
    return sequence.reduce(0, (e, r) -> r + 1);
  }

  private static Graphic colorRing(double offsetAngle, double lineWidth, double radius, Sequence<Color> palette) {
    final int sectorAngle = 360 / length(palette);
    Graphic result = emptyGraphic();
    int sectorIndex = 0;
    for (final Color color : palette) {
      final double angle = sectorIndex * sectorAngle;
      final Graphic sector = compose(
          circularSector(radius - lineWidth, sectorAngle, color),
          circularSector(radius, sectorAngle, BLACK)
      );
      result = compose(result, rotate(offsetAngle + angle, sector));
      sectorIndex++;
    }
    for (final double angle : range(0, 360, sectorAngle)) {
      Graphic ray = pin(CENTER_LEFT, rectangle(radius, lineWidth, BLACK));
      result = compose(rotate(offsetAngle + angle, ray), result);
    }
    return result;
  }

  private static Graphic colorRings(int rings, double lineWidth, double radius, Sequence<Color> palette) {
    double segmentAngle = 360.0 / length(palette);
    Graphic result = emptyGraphic();
    for (int ringIndex : range(rings)) {
      Graphic ring = colorRing(ringIndex * segmentAngle / 2.0,
          lineWidth,
          radius / rings * (ringIndex + 1),
          palette);
      result = compose(result, ring);
    }
    return result;
  }
}
