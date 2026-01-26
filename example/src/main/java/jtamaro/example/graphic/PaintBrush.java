package jtamaro.example.graphic;

import jtamaro.graphic.Color;
import jtamaro.graphic.Graphic;

import static jtamaro.data.Sequences.of;
import static jtamaro.example.Toolbelt.aboves;
import static jtamaro.example.Toolbelt.hgap;
import static jtamaro.example.Toolbelt.rightTriangle;
import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Colors.RED;
import static jtamaro.graphic.Graphics.beside;
import static jtamaro.graphic.Graphics.circularSector;
import static jtamaro.graphic.Graphics.compose;
import static jtamaro.graphic.Graphics.ellipse;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.rectangle;
import static jtamaro.graphic.Graphics.rotate;
import static jtamaro.graphic.Points.BOTTOM_LEFT;
import static jtamaro.graphic.Points.CENTER;
import static jtamaro.graphic.Points.CENTER_LEFT;
import static jtamaro.graphic.Points.CENTER_RIGHT;
import static jtamaro.io.IO.show;

public final class PaintBrush {

  private PaintBrush() {
  }

  public static void main() {
    show(paintBrush(RED));
  }

  private static Graphic trapeze(double width, double smallHeight, double largeHeight, Color color) {
    return aboves(of(
        rotate(90, rightTriangle((largeHeight - smallHeight) / 2, width, color)),
        rectangle(width, smallHeight, color),
        rotate(180, rightTriangle(width, (largeHeight - smallHeight) / 2, color))
    ));
  }

  private static Graphic stick(double length, double width, Color color) {
    final double tipLength = length / 5;
    final double endLength = length * 4 / 5;
    final double minWidth = width * 3 / 5;
    final double endDiameter = minWidth * 2 / 3;
    return compose(
        pin(CENTER,
            ellipse(endDiameter, minWidth, color)
        ),
        pin(CENTER_LEFT,
            compose(
                pin(CENTER_RIGHT,
                    trapeze(tipLength, minWidth, width, color)
                ),
                pin(CENTER_LEFT,
                    compose(
                        pin(CENTER_RIGHT,
                            rotate(180, trapeze(endLength, minWidth, width, color))
                        ),
                        pin(CENTER,
                            ellipse(endDiameter, minWidth, color)
                        )
                    )
                )
            )
        )
    );
  }

  private static Graphic brush(double diameter, Color color) {
    return compose(
        pin(BOTTOM_LEFT, circularSector(diameter / 2, 180, color)),
        circularSector(diameter, -90, color)
    );
  }

  private static Graphic paintBrush(double length, double width, Color color) {
    return compose(
        rotate(45, pin(CENTER_LEFT, beside(hgap(width * 1.5), stick(length, width, BLACK)))),
        pin(CENTER, rotate(-30, brush(width * 1.5, color)))
    );
  }

  private static Graphic paintBrush(Color color) {
    return paintBrush(400, 80, color);
  }
}
