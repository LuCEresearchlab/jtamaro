package jtamaro.en.example.graphic;

import jtamaro.en.Graphic;
import jtamaro.en.Color;
import jtamaro.en.Sequence;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Points.*;
import static jtamaro.en.IO.*;
import static jtamaro.en.Sequences.*;

public class CompositionInJava {
  
  public static void main(String[] args) {
    Sequence<Color> artsyRainbow = of(
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

    double radius = 800;
    double fontSize = radius * 0.3;
    double lineWidth = 2;
    int rings = 25;
    Graphic logo = overlay(
      above(
        renderText("Composition", "Fira Sans", fontSize, lineWidth),
        renderText("in Java", "Fira Sans", fontSize, lineWidth)
      ),
      colorRings(rings, lineWidth, radius, artsyRainbow)
    );
    show(
      overlay(logo, rectangle(width(logo) / 0.75, width(logo), TRANSPARENT))
    );
    show(
      colorRings(rings, lineWidth, radius, artsyRainbow)
    );
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
    return reduce(0, (e, r) -> r + 1, sequence);
  }

  public static Graphic colorRing(double offsetAngle, double lineWidth, double radius, Sequence<Color> palette) {
    final double sectorAngle = 360 / length(palette);
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

  public static Graphic colorRings(int rings, double lineWidth, double radius, Sequence<Color> palette) {
    double segmentAngle = 360 / length(palette);
    Graphic result = emptyGraphic();
    for (int ringIndex : range(rings)) {
        Graphic ring = colorRing(ringIndex * segmentAngle / 2, lineWidth, radius / rings * (ringIndex + 1), palette);
        result = compose(result, ring);
    }
    return result;
  }
}
