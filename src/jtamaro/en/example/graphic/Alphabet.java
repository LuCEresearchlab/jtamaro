package jtamaro.en.example.graphic;

import jtamaro.en.Color;
import jtamaro.en.Graphic;

import java.util.HashMap;
import java.util.function.BiFunction;

import static jtamaro.en.Colors.TRANSPARENT;
import static jtamaro.en.Colors.hsl;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.save;
import static jtamaro.en.IO.show;


public class Alphabet {

  // Unicode symbols to encode our glyphs:
  // ■◜◝◞◟
  private static final String[] GLYPHS = new String[]{
      // a
      "  \n" +
          "◜◝\n" +
          "◟■\n" +
          "  \n",
      // b
      "■ \n" +
          "■◝\n" +
          "◟◞\n" +
          "  \n",
      // c
      "  \n" +
          "◜ \n" +
          "◟◞\n" +
          "  \n",
      // d
      " ■\n" +
          "◜■\n" +
          "◟◞\n" +
          "  \n",
      // e
      "  \n" +
          "◜◝\n" +
          "◟ \n" +
          "  \n",
      // f
      "◜■\n" +
          "■◝\n" +
          "■ \n" +
          "  \n",
      // g
      "  \n" +
          "◜◝\n" +
          "◟■\n" +
          "■◞\n",
      // h
      "■ \n" +
          "■◝\n" +
          "■◟\n" +
          "  \n",
      // i
      " \n" +
          "■\n" +
          "■\n" +
          " \n",
      // j
      "  \n" +
          " ■\n" +
          " ■\n" +
          "■◞\n",
      // k
      "■ \n" +
          "■◞\n" +
          "■◝\n" +
          "  \n",
      // l
      "■\n" +
          "■\n" +
          "■\n" +
          " \n",
      // m
      "    \n" +
          "■◝◜◝\n" +
          "■◟◞■\n" +
          "    \n",
      // n
      "  \n" +
          "■◝\n" +
          "■◟\n" +
          "  \n",
      // o
      "  \n" +
          "◜◝\n" +
          "◟◞\n" +
          "  \n",
      // p
      "  \n" +
          "◜◝\n" +
          "■◞\n" +
          "■ \n",
      // q
      "  \n" +
          "◜◝\n" +
          "◟■\n" +
          " ■\n",
      // r
      "  \n" +
          "■◝\n" +
          "■ \n" +
          "  \n",
      // s
      "  \n" +
          "◜◞\n" +
          "◜◞\n" +
          "  \n",
      // t
      "■ \n" +
          "■◞\n" +
          "◟■\n" +
          "  \n",
      // u
      "  \n" +
          "◝■\n" +
          "◟■\n" +
          "  \n",
      // v
      "  \n" +
          "◝◜\n" +
          "◟◞\n" +
          "  \n",
      // w
      "    \n" +
          "■◜◝■\n" +
          "◟◞◟◞\n" +
          "    \n",
      // x
      "  \n" +
          "◟◞\n" +
          "◜◝\n" +
          "  \n",
      // y
      "  \n" +
          "◝■\n" +
          "◟■\n" +
          "■◞\n",
      // z
      "  \n" +
          "■◞\n" +
          "◜■\n" +
          "  \n",
  };

  private static final HashMap<Character, BiFunction<Double, Color, Graphic>> GLYPH_ELEMENTS = new HashMap<>();

  static {
    GLYPH_ELEMENTS.put(' ', (size, color) -> rectangle(size, size, TRANSPARENT));
    GLYPH_ELEMENTS.put('■', (size, color) -> rectangle(size, size, color));
    GLYPH_ELEMENTS.put('◝', (size, color) -> rotate(0, circularSector(size, 90, color)));
    GLYPH_ELEMENTS.put('◜', (size, color) -> rotate(90, circularSector(size, 90, color)));
    GLYPH_ELEMENTS.put('◟', (size, color) -> rotate(180, circularSector(size, 90, color)));
    GLYPH_ELEMENTS.put('◞', (size, color) -> rotate(270, circularSector(size, 90, color)));
  }

  private static Graphic renderLetter(char symbol, double size) {
    assert symbol == ' ' || (symbol >= 'a' && symbol <= 'z') : "Symbol '" + symbol + "' not supported.";
    if (symbol == ' ') {
      return rectangle(size, 1, TRANSPARENT);
    }
    int glyphIndex = symbol - 'a';
    String glyph = GLYPHS[glyphIndex];
    String[] glyphLines = glyph.split("\n");
    Graphic glyphGraphic = emptyGraphic();
    for (int lineIndex = 0; lineIndex < glyphLines.length; lineIndex++) {
      String line = glyphLines[lineIndex];
      Graphic row = emptyGraphic();
      for (int elementIndex = 0; elementIndex < line.length(); elementIndex++) {
        char element = line.charAt(elementIndex);
        double hue = glyphIndex * 360.0 / GLYPHS.length;
        double saturation = (lineIndex + 1) / 5.0;
        double lightness = 0.5 - 0.5 * (elementIndex / 5.0);
        Color color = hsl(hue, saturation, lightness);
        BiFunction<Double, Color, Graphic> renderer = GLYPH_ELEMENTS.get(element);
        row = beside(row, renderer.apply(size, color));
      }
      glyphGraphic = above(glyphGraphic, row);
    }
    return glyphGraphic;
  }

  private static Graphic renderString(String text, double size) {
    Graphic gap = rectangle(size / 10, 1, TRANSPARENT);
    Graphic result = gap;
    for (char symbol : text.toCharArray()) {
      result = beside(result, beside(renderLetter(symbol, size), gap));
    }
    return result;
  }

  public static void main(String[] args) {
    Graphic rendering = renderString("pf", 200);
    show(rendering);
    save(overlay(rendering, rectangle(rendering.getWidth() + 50, rendering.getHeight() + 50, TRANSPARENT)), "pf2.png");
  }

}
