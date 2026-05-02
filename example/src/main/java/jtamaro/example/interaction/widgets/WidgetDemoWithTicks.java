package jtamaro.example.interaction.widgets;

import jtamaro.optics.Glasses;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.of;
import static jtamaro.graphic.Colors.TRANSPARENT;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.rgb;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.overlay;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.GraphicIO.interact;
import static jtamaro.example.interaction.widgets.Widgets.*;


public class WidgetDemoWithTicks {

  public static void main() {
    final DemoModel model = new DemoModel(true, 3, 255, 127, 0, 0);
    interact(model)
      .withRenderer(WidgetDemoWithTicks::ui)
      .withTickHandler(WidgetDemoWithTicks::onTick)
      .withMsBetweenTicks(10)
      .run();
  }

  private static DemoModel onTick(DemoModel m) {
    return m.rotate()
      ? WidgetDemoWithTicks$DemoModelOptics.angle.over(a -> (a + m.speed()) % 360, m)
      : m;
  }

  @Glasses
  static record DemoModel(boolean rotate, int speed, int red, int green, int blue, int angle) { }

  private static Graphic ui(DemoModel m) {
    // margin, column, row, checkbox, label, slider, grid, debug are "GUI Widgets"
    // (but they really are just functions that produce graphics)
    return margin(
      column(of(
        row(of(
          checkbox("Rotate", WidgetDemoWithTicks$DemoModelOptics.rotate, m),
          label("Speed: "),
          slider(1, 10, WidgetDemoWithTicks$DemoModelOptics.speed, m),
          label("" + m.speed(), 40)
        )),
        label("Color:"),
        grid(of(
          of(label("Red:"), slider(0, 255, WidgetDemoWithTicks$DemoModelOptics.red, m), label("" + m.red(), 40)),
          of(label("Green:"), slider(0, 255, WidgetDemoWithTicks$DemoModelOptics.green, m), label("" + m.green(), 40)),
          of(label("Blue:"), slider(0, 255, WidgetDemoWithTicks$DemoModelOptics.blue, m), label("" + m.blue(), 40))
        )),
        // here is what in a traditional GUI toolkit would be called a "custom component"
        // (but for us, it's simply a normal graphic)
        rotate(m.angle(),
          overlay(
            rectangle(40, 40, rgb(m.red(), m.green(), m.blue())),
            ellipse(60, 60, BLACK)
          )
        ),
        // a GUI widget that just shows a string representation of the given object; useful for debugging
        debug(m, 50)
      ))
    );
  }

}
