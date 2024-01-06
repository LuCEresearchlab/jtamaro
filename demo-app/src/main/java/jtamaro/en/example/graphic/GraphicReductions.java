package jtamaro.en.example.graphic;

import static jtamaro.en.Colors.*;
import static jtamaro.en.Graphics.*;
import static jtamaro.en.IO.*;
import static jtamaro.en.Sequences.*;
import static jtamaro.en.example.Toolbelt.*;


public class GraphicReductions {
  
  public static void main(String[] args) {
    show(besides(of(
      text("Left", "Helvetica", 100, RED),
      text("Middle", "Helvetica", 100, GREEN),
      text("Right", "Helvetica", 100, BLUE)
    )));
    show(aboves(of(
      text("Top", "Helvetica", 100, RED),
      text("Middle", "Helvetica", 100, GREEN),
      text("Bottom", "Helvetica", 100, BLUE)
    )));
    show(overlays(of(
      text("Front", "Helvetica", 100, RED),
      text("Middle", "Helvetica", 100, GREEN),
      text("Back", "Helvetica", 100, BLUE)
    )));
  }

}
