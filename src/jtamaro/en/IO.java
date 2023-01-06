package jtamaro.en;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import jtamaro.en.oo.AbstractGraphic;
import jtamaro.internal.gui.GraphicFrame;
import jtamaro.internal.gui.RenderOptions;
import jtamaro.internal.representation.GraphicImpl;


public class IO {

  public static void show(Graphic graphic) {
    final GraphicFrame frame = new GraphicFrame();
    frame.setGraphic(((AbstractGraphic)graphic).getImplementation());
    frame.setVisible(true);
  }

  public static void save(Graphic graphic, String filename) {
    GraphicImpl graphicImpl = ((AbstractGraphic)graphic).getImplementation();
    int width = (int)graphicImpl.getWidth();
    int height = (int)graphicImpl.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    RenderOptions renderOptions = new RenderOptions(0);
    g2.translate(-graphicImpl.getBBox().getMinX(), -graphicImpl.getBBox().getMinY());
    graphicImpl.render(g2, renderOptions);
    //graphicImpl.drawDebugInfo(g2, renderOptions);
    try {
      ImageIO.write(bufferedImage, "png", new File(filename));
    } catch (IOException ex) {
      System.err.println("Error saving image to " + filename + ": " + ex.getMessage());
    }
  }

}
