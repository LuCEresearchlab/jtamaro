package jtamaro.example.interaction.widgets;

import jtamaro.optics.Glasses;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.of;
import static jtamaro.graphic.Graphics.above;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.io.GraphicIO.interact;
import static jtamaro.example.interaction.widgets.Widgets.*;


public class WidgetDemo {

  public static void main() {
    final DemoModel model = new DemoModel(true, 5);
    interact(model).withRenderer(WidgetDemo::ui).run();
  }

  @Glasses
  static record DemoModel(Boolean b, Integer i) { }

  private static Graphic ui(DemoModel m) {
    return above(
      row(of(
        checkbox("Check me", WidgetDemo$DemoModelOptics.b, m),
        label("Size: "),
        slider(0, 10, WidgetDemo$DemoModelOptics.i, m),
        label("" + m.i())
      )),
      label(m.toString())
    );
  }

}
