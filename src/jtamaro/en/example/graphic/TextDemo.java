package jtamaro.en.example.graphic;

import jtamaro.en.Graphic;

import static jtamaro.en.Colors.BLACK;
import static jtamaro.en.Graphics.pin;
import static jtamaro.en.Graphics.text;
import static jtamaro.en.IO.show;
import static jtamaro.en.Points.BOTTOM_RIGHT;

public class TextDemo {

  public static void main(String[] args) {
    Graphic text = text("up!", "Helvetica", 400, BLACK);
    show(pin(BOTTOM_RIGHT, text));
  }

}
