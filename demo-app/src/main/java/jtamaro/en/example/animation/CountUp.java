package jtamaro.en.example.animation;

import jtamaro.en.Sequence;
import jtamaro.en.Graphic;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.IO.*;


public class CountUp {

  public static void main(String[] args) {
    Sequence<Graphic> frames = map(
        t -> overlay(
            text("" + (t / 100), "FiraSans", 100, BLACK),
            compose(
                circularSector(100, (t * 360 / 100) % 360, RED),
                rectangle(200, 200, WHITE))),
        from(0));
    animate(frames, false, 10);
    showFilmStrip(take(200, frames));
    // saveAnimatedGif(take(100, frames), true, 10, "countup.gif");
  }

}
