package jtamaro.en.graphic;

import jtamaro.en.Color;
import jtamaro.internal.representation.TextImpl;


public final class Text extends AbstractGraphic {

  public Text(String content, String font, double points, Color color) {
    super(new TextImpl(content, font, points, color.getImplementation()));
  }

}
