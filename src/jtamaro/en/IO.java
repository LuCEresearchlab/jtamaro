package jtamaro.en;

import jtamaro.en.oo.AbstractGraphic;
import jtamaro.internal.gui.GraphicFrame;


public class IO {

  public static void show(Graphic graphic) {
    final GraphicFrame frame = new GraphicFrame();
    frame.setGraphic(((AbstractGraphic)graphic).getImplementation());
    frame.setVisible(true);
  }

}
