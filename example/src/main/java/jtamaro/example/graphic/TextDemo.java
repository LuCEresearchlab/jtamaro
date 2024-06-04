package jtamaro.example.graphic;

import jtamaro.graphic.Fonts;
import jtamaro.graphic.Graphic;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Graphics.pin;
import static jtamaro.graphic.Graphics.text;
import static jtamaro.graphic.Points.BOTTOM_RIGHT;
import static jtamaro.io.IO.show;

public class TextDemo {

  public static void main(String[] args) {
    Graphic text = text("up!", Fonts.SANS_SERIF, 400, BLACK);
    show(pin(BOTTOM_RIGHT, text));
  }

}
