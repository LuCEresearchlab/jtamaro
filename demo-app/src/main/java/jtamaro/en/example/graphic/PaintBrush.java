package jtamaro.en.example.graphic;

import jtamaro.en.Sequence;
import jtamaro.en.Color;
import jtamaro.en.Graphic;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.show;
import static jtamaro.en.Colors.*;
import static jtamaro.en.Points.*;

import static jtamaro.en.example.Toolbelt.*;


// check README.md
public class PaintBrush {
    
    public static Graphic trapeze(double width, double smallHeight, double largeHeight, Color color) {
        return aboves(of(
            rotate(90, rightTriangle((largeHeight-smallHeight)/2, width, color)),
            rectangle(width, smallHeight, color),
            rotate(180, rightTriangle(width, (largeHeight-smallHeight)/2, color))
        ));
    }
    
    public static Graphic stick(double length, double width, Color color) {
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
    
    public static Graphic brush(double diameter, Color color) {
        return compose(
            pin(BOTTOM_LEFT, circularSector(diameter / 2, 180, color)),
            circularSector(diameter, -90, color)
        );
    }
    
    public static Graphic paintBrush(double length, double width, Color color) {
        return compose(
            rotate(45, pin(CENTER_LEFT, beside(hgap(width * 1.5), stick(length, width, BLACK)))),
            pin(CENTER, rotate(-30, brush(width * 1.5, color)))
        );
    }

    public static Graphic paintBrush(Color color) {
        return paintBrush(400, 80, color);
    }

    public static void main(String[] args) {
      show(paintBrush(RED));
    }
    
}
