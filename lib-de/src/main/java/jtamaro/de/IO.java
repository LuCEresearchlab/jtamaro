package jtamaro.de;

import jtamaro.de.oo.AbstrakteGrafik;
import jtamaro.internal.gui.GraphicFrame;


public class IO {

  public static void zeige(Grafik grafik) {
    final GraphicFrame frame = new GraphicFrame();
    frame.setGraphic(((AbstrakteGrafik) grafik).getImplementation());
    frame.setVisible(true);
  }

}
