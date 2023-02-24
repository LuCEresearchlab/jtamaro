package jtamaro.en.example.graphic;

import jtamaro.en.Graphic;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.Points.*;
import static jtamaro.en.Colors.*;
import static jtamaro.en.IO.*;

public class TextDemo {

  public static void main(String[] args) {
    Graphic text = text("up!", "Helvetica", 400, BLACK);
    show(pin(BOTTOM_RIGHT, text));
  }

}
