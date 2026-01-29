package jtamaro.example.animation;

import jtamaro.data.Sequence;
import jtamaro.graphic.Fonts;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.range;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.GraphicIO.animate;
import static jtamaro.io.GraphicIO.showFilmStrip;

public final class CountUp {

  private CountUp() {
  }

  public static void main() {
    final Sequence<Graphic> frames = range(0, 1 << 10).map(t -> overlay(
        text("" + (t / 100), Fonts.SANS_SERIF, 100, BLACK),
        compose(
            circularSector(100, (t * 360.0 / 100) % 360, RED),
            rectangle(200, 200, WHITE))));
    animate(frames, false, 10);
    showFilmStrip(frames.take(200));
  }
}
