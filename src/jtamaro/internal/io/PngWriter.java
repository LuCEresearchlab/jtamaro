package jtamaro.internal.io;

import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import jtamaro.internal.gui.RenderOptions;
import jtamaro.internal.representation.GraphicImpl;


public class PngWriter {
  
  public static void save(GraphicImpl graphicImpl, String filename) throws IOException {      
    int width = (int)graphicImpl.getWidth();
    int height = (int)graphicImpl.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    RenderOptions renderOptions = new RenderOptions(0);
    g2.translate(-graphicImpl.getBBox().getMinX(), -graphicImpl.getBBox().getMinY());
    graphicImpl.render(g2, renderOptions);
    //graphicImpl.drawDebugInfo(g2, renderOptions);
    ImageIO.write(bufferedImage, "png", new File(filename));
  }

  public static void save(GraphicImpl graphicImpl, String filename, RenderOptions renderOptions) throws IOException {      
    int padding = renderOptions.getPadding();
    int width = (int)graphicImpl.getWidth() + 2 * padding;
    int height = (int)graphicImpl.getHeight() + 2 * padding;
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    g2.translate(padding, padding);
    g2.translate(-graphicImpl.getBBox().getMinX(), -graphicImpl.getBBox().getMinY());
    graphicImpl.render(g2, renderOptions);
    graphicImpl.drawDebugInfo(g2, renderOptions);
    ImageIO.write(bufferedImage, "png", new File(filename));
  }

}
