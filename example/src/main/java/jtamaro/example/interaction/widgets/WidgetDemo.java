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
import static jtamaro.graphic.Graphics.text;
import static jtamaro.io.GraphicIO.interact;

import java.awt.Font;

import static jtamaro.example.interaction.widgets.Widgets.*;


public class WidgetDemo {

  public static void main() {
    final DemoModel model = new DemoModel(true, 30, 255, 0, 0);
    interact(model).withRenderer(WidgetDemo::ui).run();
  }

  @Glasses
  static record DemoModel(Boolean outline, Integer size, Integer red, Integer green, Integer blue) { }

  private static Graphic ui(DemoModel m) {
    return margin(
      column(of(
        row(of(
          checkbox("Outline", WidgetDemo$DemoModelOptics.outline, m),
          label("Size: "),
          slider(20, 40, WidgetDemo$DemoModelOptics.size, m),
          label("" + m.size(), 40)
        )),
        label("Color:"),
        grid(of(
          of(label("Red:"), slider(0, 255, WidgetDemo$DemoModelOptics.red, m), label("" + m.red(), 40)),
          of(label("Green:"), slider(0, 255, WidgetDemo$DemoModelOptics.green, m), label("" + m.green(), 40)),
          of(label("Blue:"), slider(0, 255, WidgetDemo$DemoModelOptics.blue, m), label("" + m.blue(), 40))
        )),
        overlay(
          ellipse(m.size(), m.size(), rgb(m.red(), m.green(), m.blue())),
          ellipse(m.size() + 4, m.size() + 4, m.outline() ? BLACK : TRANSPARENT)
        ),
        modelAsText(m, 40)
      ))
    );
  }

}
