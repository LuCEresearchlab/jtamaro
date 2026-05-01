package jtamaro.example.interaction.widgets;

import static jtamaro.graphic.Colors.BLACK;
import static jtamaro.graphic.Fonts.SANS_SERIF;
import static jtamaro.graphic.Graphics.text;

import jtamaro.graphic.Graphic;


public class Label {

  private static final int FONT_SIZE = 20;

  public static Graphic create(String label) {
    return text(label, SANS_SERIF, FONT_SIZE, BLACK);
  }

}
