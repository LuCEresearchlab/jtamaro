package jtamaro.en.example.graphic;

import static jtamaro.en.Sequences.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.show;
import static jtamaro.en.example.Toolbelt.*;

import jtamaro.en.Graphic;

public class Rings {

  public static void main(String[] args) {
    Graphic dotRing = ring(
      400, 
      circle(20, JT_RED), 
      36
    );
    show(dotRing);

    Graphic dotHalfRing = ringSector(
      400, 
      0, 
      180, 
      circle(20, JT_RED), 
      18
    );
    show(dotHalfRing);

    Graphic dotInterleavedRing = ringList(
      400, 
      times(24, of(circle(20, JT_RED), square(20, JT_BLUE)))
    );
    show(dotInterleavedRing);

    Graphic colorDots = ringList(
      400, 
      map(
        h -> circle(20, hsv(h, 1, 1)),
        range(0, 360, 10)
      )
    );
    show(colorDots);

    Graphic colorDotsHighlighted = composes(of(
      ringList(
        400, 
        map(
          h -> circle(20, hsv(h, 1, 1)),
          range(0, 360, 10)
        )
      ),
      ring(
        400 - (40 / 2), 
        circle(40, BLACK), 
        3
      ),
      rotate(60, 
        ring(
          400 - (40 / 2), 
          circle(40, WHITE), 
          3
        )
      )
    ));
    show(colorDotsHighlighted);

    final int count = 12;
    Graphic colorWheel = ringList(
      0, 
      map(
        i -> rotate(
          -(360 / count / 2), 
          circularSector(200, 360 / count, hsv(i * 360 / count, 1, 1))
        ),
        range(count)
      )
    );
    show(colorWheel);

    Graphic nested2 = ring(400, ring(40, circle(10, JT_RED), 12), 24);
    show(nested2);

    Graphic nested3 = ring(400, ring(80, ring(40, circle(10, JT_RED), 10), 6), 8);
    show(nested3);

    Graphic satellite1 = ringSectorList(200, -90, 90, times(6, of(circle(20, JT_BLUE), square(20, JT_RED))));
    Graphic satellite2 = ringSectorList(200, -90, 90, times(6, of(circle(20, JT_YELLOW), square(20, JT_BROWN))));
    Graphic nestedSpecial = ringList(
        400, 
        times(3, of(
            satellite1,
            satellite2
         ))
    );
    show(nestedSpecial);
  }

}
