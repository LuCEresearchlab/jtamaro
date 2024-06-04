package jtamaro.example.animation;

import jtamaro.data.Sequence;
import jtamaro.graphic.Fonts;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.map;
import static jtamaro.data.Sequences.range;
import static jtamaro.data.Sequences.take;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.IO.animate;
import static jtamaro.io.IO.showFilmStrip;

public class CountUp {

  public static void main(String[] args) {
    Sequence<Graphic> frames = map(
        t -> overlay(
            text("" + (t / 100), Fonts.SANS_SERIF, 100, BLACK),
            compose(
                circularSector(100, (t * 360.0 / 100) % 360, RED),
                rectangle(200, 200, WHITE))),
        range(0, 1 << 10));
    animate(frames, false, 10);
    showFilmStrip(take(200, frames));
    // saveAnimatedGif(take(100, frames), true, 10, "countup.gif");
  }

}
