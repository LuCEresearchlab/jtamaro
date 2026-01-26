package jtamaro.example.graphic;

import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.range;
import static jtamaro.example.Toolbelt.JT_BLUE;
import static jtamaro.example.Toolbelt.JT_BROWN;
import static jtamaro.example.Toolbelt.JT_RED;
import static jtamaro.example.Toolbelt.JT_YELLOW;
import static jtamaro.example.Toolbelt.circle;
import static jtamaro.example.Toolbelt.composes;
import static jtamaro.example.Toolbelt.ring;
import static jtamaro.example.Toolbelt.ringList;
import static jtamaro.example.Toolbelt.ringSector;
import static jtamaro.example.Toolbelt.ringSectorList;
import static jtamaro.example.Toolbelt.square;
import static jtamaro.example.Toolbelt.times;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.hsv;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.io.IO.show;

public final class Rings {

  private Rings() {
  }

  public static void main() {
    final Graphic dotRing = ring(
        400,
        circle(20, JT_RED),
        36
    );
    show(dotRing);

    final Graphic dotHalfRing = ringSector(
        400,
        0,
        180,
        circle(20, JT_RED),
        18
    );
    show(dotHalfRing);

    final Graphic dotInterleavedRing = ringList(
        400,
        times(24, of(circle(20, JT_RED), square(20, JT_BLUE)))
    );
    show(dotInterleavedRing);

    final Graphic colorDots = ringList(
        400,
        range(0, 360, 10).map(h -> circle(20, hsv(h, 1, 1)))
    );
    show(colorDots);

    final Graphic colorDotsHighlighted = composes(of(
        ringList(
            400,
            range(0, 360, 10).map(h -> circle(20, hsv(h, 1, 1)))
        ),
        ring(
            400 - (40.0 / 2),
            circle(40, BLACK),
            3
        ),
        rotate(60,
            ring(
                400 - (40.0 / 2),
                circle(40, WHITE),
                3
            )
        )
    ));
    show(colorDotsHighlighted);

    final int count = 12;
    final Graphic colorWheel = ringList(
        0,
        range(count).map(i -> rotate(
            -(360.0 / count / 2.0),
            circularSector(200, 360.0 / count, hsv(i * 360.0 / count, 1, 1)))));
    show(colorWheel);

    final Graphic nested2 = ring(400, ring(40, circle(10, JT_RED), 12), 24);
    show(nested2);

    final Graphic nested3 = ring(400, ring(80, ring(40, circle(10, JT_RED), 10), 6), 8);
    show(nested3);

    final Graphic satellite1 = ringSectorList(200,
        -90,
        90,
        times(6, of(circle(20, JT_BLUE), square(20, JT_RED))));
    final Graphic satellite2 = ringSectorList(200,
        -90,
        90,
        times(6, of(circle(20, JT_YELLOW), square(20, JT_BROWN))));
    final Graphic nestedSpecial = ringList(
        400,
        times(3, of(
            satellite1,
            satellite2
        ))
    );
    show(nestedSpecial);
  }
}
