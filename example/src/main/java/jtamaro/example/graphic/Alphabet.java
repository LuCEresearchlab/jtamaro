package jtamaro.example.graphic;

import java.util.HashMap;
import jtamaro.data.Function2;
import jtamaro.example.Toolbelt;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

import static jtamaro.example.Toolbelt.square;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.hsl;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Points.BOTTOM_LEFT;
import static jtamaro.io.GraphicIO.show;

public final class Alphabet {

  // Unicode symbols to encode our glyphs:
  // ■◜◝◞◟
  private static final String[] GLYPHS = new String[]{
      // a
      "  \n◜◝\n◟■\n  \n",
      // b
      "■ \n■◝\n◟◞\n  \n",
      // c
      "  \n◜ \n◟◞\n  \n",
      // d
      " ■\n◜■\n◟◞\n  \n",
      // e
      "  \n◜◝\n◟ \n  \n",
      // f
      "◜■\n■◝\n■ \n  \n",
      // g
      "  \n◜◝\n◟■\n■◞\n",
      // h
      "■ \n■◝\n■◟\n  \n",
      // i
      " \n■\n■\n \n",
      // j
      "  \n ■\n ■\n■◞\n",
      // k
      "■ \n■◞\n■◝\n  \n",
      // l
      "■\n■\n■\n \n",
      // m
      "    \n■◝◜◝\n■◟◞■\n    \n",
      // n
      "  \n■◝\n■◟\n  \n",
      // o
      "  \n◜◝\n◟◞\n  \n",
      // p
      "  \n◜◝\n■◞\n■ \n",
      // q
      "  \n◜◝\n◟■\n ■\n",
      // r
      "  \n■◝\n■ \n  \n",
      // s
      "  \n◜◞\n◜◞\n  \n",
      // t
      "■ \n■◞\n◟■\n  \n",
      // u
      "  \n◝■\n◟■\n  \n",
      // v
      "  \n◝◜\n◟◞\n  \n",
      // w
      "    \n■◜◝■\n◟◞◟◞\n    \n",
      // x
      "  \n◟◞\n◜◝\n  \n",
      // y
      "  \n◝■\n◟■\n■◞\n",
      // z
      "  \n■◞\n◜■\n  \n",
  };

  private static final HashMap<Character, Function2<Double, Color, Graphic>>
      GLYPH_ELEMENTS = new HashMap<>();

  private static final HashMap<Character, Function2<Double, Color, Graphic>>
      OUTLINE_GLYPH_ELEMENTS = new HashMap<>();

  private static final Color OUTLINE_COLOR = BLACK;

  private static final double OUTLINE_FRACTION = 0.1;

  static {
    GLYPH_ELEMENTS.put(' ', (size, color) -> square(size, TRANSPARENT));
    GLYPH_ELEMENTS.put('■', Toolbelt::square);
    GLYPH_ELEMENTS.put('◝', (size, color) -> rotate(0, circularSector(size, 90, color)));
    GLYPH_ELEMENTS.put('◜', (size, color) -> rotate(90, circularSector(size, 90, color)));
    GLYPH_ELEMENTS.put('◟', (size, color) -> rotate(180, circularSector(size, 90, color)));
    GLYPH_ELEMENTS.put('◞', (size, color) -> rotate(270, circularSector(size, 90, color)));
    OUTLINE_GLYPH_ELEMENTS.put(' ', (size, color) -> square(size, TRANSPARENT));
    OUTLINE_GLYPH_ELEMENTS.put('■', Alphabet::outlinedSquare);
    OUTLINE_GLYPH_ELEMENTS.put('◝', (size, color) -> rotate(0, outlinedSector(size, color)));
    OUTLINE_GLYPH_ELEMENTS.put('◜', (size, color) -> rotate(90, outlinedSector(size, color)));
    OUTLINE_GLYPH_ELEMENTS.put('◟', (size, color) -> rotate(180, outlinedSector(size, color)));
    OUTLINE_GLYPH_ELEMENTS.put('◞', (size, color) -> rotate(270, outlinedSector(size, color)));
  }

  private Alphabet() {
  }

  public static void main() {
    final Graphic rendering = renderString("pf", 200, true);
    show(rendering);
  }

  private static Graphic outlinedSquare(double size, Color color) {
    return overlay(
        square(size * (1 - 2 * OUTLINE_FRACTION), color),
        square(size, OUTLINE_COLOR)
    );
  }

  private static Graphic outlinedSector(double size, Color color) {
    return compose(
        compose(
            pin(
                BOTTOM_LEFT,
                rectangle(size * (1 - OUTLINE_FRACTION), size * OUTLINE_FRACTION, BLACK)
            ),
            pin(
                BOTTOM_LEFT,
                rectangle(size * OUTLINE_FRACTION, size * (1 - OUTLINE_FRACTION), BLACK)
            )
        ),
        compose(
            pin(BOTTOM_LEFT, circularSector(size * (1 - OUTLINE_FRACTION), 90, color)),
            pin(BOTTOM_LEFT, circularSector(size, 90, OUTLINE_COLOR))
        )
    );
  }

  public static Graphic renderLetter(char symbol, double size, boolean outlined) {
    assert symbol == ' ' || (symbol >= 'a' && symbol <= 'z') : "Symbol '"
        + symbol
        + "' not supported.";
    if (symbol == ' ') {
      return rectangle(size, 1, TRANSPARENT); // TODO: height: 0
    }
    final int glyphIndex = symbol - 'a';
    final String glyph = GLYPHS[glyphIndex];
    final String[] glyphLines = glyph.split("\n");
    Graphic glyphGraphic = emptyGraphic();
    for (int lineIndex = 0; lineIndex < glyphLines.length; lineIndex++) {
      String line = glyphLines[lineIndex];
      Graphic row = emptyGraphic();
      for (int elementIndex = 0; elementIndex < line.length(); elementIndex++) {
        final char element = line.charAt(elementIndex);
        final double hue = glyphIndex * 360.0 / GLYPHS.length;
        final double saturation = (lineIndex + 1) / 5.0;
        final double lightness = 0.5 - 0.5 * (elementIndex / 5.0);
        final Color color = outlined ? WHITE : hsl(hue, saturation, lightness);
        final Function2<Double, Color, Graphic> renderer = outlined
            ? OUTLINE_GLYPH_ELEMENTS.get(element)
            : GLYPH_ELEMENTS.get(element);
        row = beside(row, renderer.apply(size, color));
      }
      glyphGraphic = above(glyphGraphic, row);
    }
    return glyphGraphic;
  }

  public static Graphic renderString(String text, double size, boolean outlined) {
    final Graphic gap = rectangle(size / 10, 1, TRANSPARENT);
    Graphic result = gap;
    for (char symbol : text.toCharArray()) {
      result = beside(result, beside(renderLetter(symbol, size, outlined), gap));
    }
    return result;
  }
}
