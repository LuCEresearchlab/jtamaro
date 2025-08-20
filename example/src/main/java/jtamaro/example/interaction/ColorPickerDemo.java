package jtamaro.example.interaction;

import java.awt.event.KeyEvent;
import jtamaro.data.Sequence;
import jtamaro.data.Triplet;
import jtamaro.graphic.Actionable;
import jtamaro.graphic.CartesianWorld;
import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;
import jtamaro.interaction.Coordinate;
import jtamaro.interaction.KeyboardKey;
import jtamaro.interaction.MouseButton;

import static jtamaro.data.Sequences.of;
import static jtamaro.data.Sequences.range;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.WHITE;
import static jtamaro.graphic.Colors.hsl;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.emptyGraphic;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.io.IO.interact;

public class ColorPickerDemo {

  public static final double RADIUS = 200;

  public static final double LIGHTNESS = 0.5;

  private sealed interface Harmony {

    Sequence<Color> compute(Color main);

    record Complementary() implements Harmony {

      @Override
      public Sequence<Color> compute(Color main) {
        final Triplet<Double, Double, Double> components = rgbToHsl(main);
        return of(
            main,
            hsl(
                (components.first() + 180.0) % 360.0,
                components.second(),
                components.third()
            )
        );
      }
    }

    record Mono() implements Harmony {

      @Override
      public Sequence<Color> compute(Color main) {
        final Triplet<Double, Double, Double> components = rgbToHsl(main);
        return of(
            hsl(
                components.first(),
                components.second() / 3.0,
                components.third()
            ),
            hsl(
                components.first(),
                components.second() / 3.0 * 2.0,
                components.third()
            ),
            main
        );
      }
    }

    record Triadic() implements Harmony {

      @Override
      public Sequence<Color> compute(Color main) {
        final Triplet<Double, Double, Double> components = rgbToHsl(main);
        return of(
            main,
            hsl(
                (components.first() + 120) % 360.0,
                components.second(),
                components.third()
            ),
            hsl(
                (components.first() + 240.0) % 360.0,
                components.second(),
                components.third()
            )
        );
      }
    }

  }

  private record Model(
      Color color,
      Harmony harmony
  ) {

    public Model withColor(Color color) {
      return new Model(color, harmony);
    }

    public Model withHarmony(Harmony harmony) {
      return new Model(color, harmony);
    }
  }

  private static Triplet<Double, Double, Double> rgbToHsl(Color color) {
    final double r = color.red() / 255.0;
    final double g = color.green() / 255.0;
    final double b = color.blue() / 255.0;

    final double max = Math.max(Math.max(r, g), b);
    final double min = Math.min(Math.min(r, g), b);

    final double h;
    final double s;
    final double l;

    if (max == min) {
      h = 0.0;
      s = 0.0;
      l = 0.0;
    } else {
      final double space = max + min;
      final double delta = max - min;
      h = (
          (max == r ? (g - b) / delta + (g < b ? 6.0 : 0.0)
              : max == g ? (b - r) / delta + 2.0
                  // max == b
                  : (r - g) / delta + 4.0)
              / 6.0
      ) * 360.0;
      s = space > 1
          ? delta / (2 - delta)
          : delta / space;
      l = space / 2.0;
    }
    return new Triplet<>(h, s, l);
  }

  private static Graphic colorWheel() {
    final Sequence<Integer> angles = range(0, 360, 5);
    return range(((int) RADIUS) - 1, -1, -10).reduce(
        emptyGraphic(),
        (s, wheelAcc) -> angles.reduce(
            wheelAcc,
            (angle, ringAcc) -> compose(
                ringAcc,
                rotate(
                    angle,
                    circularSector(s, 5, hsl(angle, s / RADIUS, LIGHTNESS))
                )
            )
        )
    );
  }

  private static Model onKeyEvent(Model model, KeyboardKey key) {
    return switch (key.keyCode()) {
      case KeyEvent.VK_1 -> model.withHarmony(new Harmony.Complementary());
      case KeyEvent.VK_2 -> model.withHarmony(new Harmony.Mono());
      case KeyEvent.VK_3 -> model.withHarmony(new Harmony.Triadic());
      default -> model;
    };
  }

  private static Model onDrag(Model m, Coordinate coords) {
    final double x = coords.x() / RADIUS;
    final double y = coords.y() / RADIUS;
    final double angle = Math.toDegrees(Math.atan2(y, x));
    final double h = angle < 0 ? angle + 360.0 : angle;
    final double s = Math.hypot(x, y);
    return m.withColor(hsl(h, s, LIGHTNESS));
  }

  private static Graphic render(Model model) {
    final Graphic g = model.harmony.compute(model.color).reduce(
        new CartesianWorld(),
        (color, cw) -> {
          final boolean isMain = color.equals(model.color);
          final Graphic itemBg = ellipse(12.0, 12.0, isMain ? BLACK : WHITE);
          final Graphic itemFg = ellipse(10.0, 10.0, color);
          final Triplet<Double, Double, Double> hsl = rgbToHsl(color);
          final double x = Math.cos(Math.toRadians(hsl.first())) * hsl.second() * RADIUS;
          final double y = Math.sin(Math.toRadians(hsl.first())) * hsl.second() * RADIUS;
          return cw.place(x, y, overlay(itemFg, itemBg));
        }
    ).asGraphic();

    return new Actionable<Model>(
        compose(
            g,
            // "background" for clicking outside the color dots
            circularSector(RADIUS, 360, TRANSPARENT)
        )
    ).withMouseDragHandler((coords, $) -> onDrag(model, coords)).asGraphic();
  }

  public static void main(String[] args) {
    final Model initialModel = new Model(hsl(1, 0.7, 0.5), new Harmony.Complementary());
    interact(initialModel)
        .withRenderer(ColorPickerDemo::render)
        .withBackground(colorWheel())
        .withKeyPressHandler(ColorPickerDemo::onKeyEvent)
        .run();
  }
}
