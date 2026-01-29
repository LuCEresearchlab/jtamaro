package jtamaro.example.graphic;

import jtamaro.graphic.Fonts;

import static jtamaro.data.Sequences.of;
import static jtamaro.example.Toolbelt.aboves;
import static jtamaro.example.Toolbelt.besides;
import static jtamaro.example.Toolbelt.overlays;
import static jtamaro.graphic.Colors.BLUE;
import static jtamaro.graphic.Colors.GREEN;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.GraphicIO.show;

public final class GraphicReductions {

  private GraphicReductions() {
  }

  public static void main() {
    show(besides(of(
        text("Left", Fonts.SANS_SERIF, 100, RED),
        text("Middle", Fonts.SANS_SERIF, 100, GREEN),
        text("Right", Fonts.SANS_SERIF, 100, BLUE)
    )));
    show(aboves(of(
        text("Top", Fonts.SANS_SERIF, 100, RED),
        text("Middle", Fonts.SANS_SERIF, 100, GREEN),
        text("Bottom", Fonts.SANS_SERIF, 100, BLUE)
    )));
    show(overlays(of(
        text("Front", Fonts.SANS_SERIF, 100, RED),
        text("Middle", Fonts.SANS_SERIF, 100, GREEN),
        text("Back", Fonts.SANS_SERIF, 100, BLUE)
    )));
  }
}
